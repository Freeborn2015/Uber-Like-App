package com.easyride.urbanBusTransit.services;

import com.easyride.urbanBusTransit.data.dtos.requests.CarRegistrationRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.CreateDriverRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.DriversLoginRequest;
import com.easyride.urbanBusTransit.data.dtos.responses.CarRegistrationResponse;
import com.easyride.urbanBusTransit.data.dtos.responses.DriversResponse;
import com.easyride.urbanBusTransit.data.models.Car;
import com.easyride.urbanBusTransit.data.models.Driver;
import com.easyride.urbanBusTransit.data.models.Location;
import com.easyride.urbanBusTransit.data.models.enumm.DriverStatus;
import com.easyride.urbanBusTransit.data.models.token.TokenVerification;
import com.easyride.urbanBusTransit.data.repositories.CarReposiory;
import com.easyride.urbanBusTransit.data.repositories.DriverRepository;
import com.easyride.urbanBusTransit.data.repositories.TokenVerificationRepository;
import com.easyride.urbanBusTransit.exceptions.EmailSender;
import com.easyride.urbanBusTransit.validators.Validator;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class DriverServiceImpl implements DriverService{
    @Autowired
    DriverRepository driverRepository;
   @Autowired
   TokenVerificationRepository tokenVerificationRepository;



    @Autowired
    CarReposiory carReposiory;

    @Autowired
    EmailSender emailSender;

    //private  final String SECRET_KEY = System.getenv("API_KEY");

    @Override
    public DriversResponse createDriver(CreateDriverRequest createDriverRequest) throws MessagingException {
        if (!Validator.isValidEmailAddress(createDriverRequest.getEmailAddress()))
            throw new RuntimeException(String.format("%s email is weak ", createDriverRequest.getEmailAddress()));
        if (!Validator.isValidPassword(createDriverRequest.getPassword()))
            throw new RuntimeException(String.format("%s password is weak please try again", createDriverRequest.getPassword()));
        if (!Validator.isValidPhoneNumber(createDriverRequest.getPhoneNumber()))
            throw new RuntimeException(String.format("%s number must be 11 digit", createDriverRequest.getPhoneNumber()));
        Driver driver = new Driver();
        if(driverRepository.findDriverByEmailAddress(createDriverRequest.getEmailAddress()).isPresent())
            throw new  RuntimeException("name already exist");
        else
            driver.setEmailAddress(createDriverRequest.getEmailAddress());

        driver.setFirstName(createDriverRequest.getFirstName());
        driver.setLastName(createDriverRequest.getLastName());
        driver.setPhoneNumber(createDriverRequest.getPhoneNumber());
        driver.setDriversLicense(createDriverRequest.getDriversLicense());
        driver.setLocation(createDriverRequest.getLocation());
        driver.setDriverStatus(DriverStatus.AVAILABLE);
        String password = BCrypt.hashpw(createDriverRequest.getPassword(),BCrypt.gensalt());
        driver.setPassword(password);

        Driver savedDriver = driverRepository.save(driver);
        String token = new DecimalFormat("000000").format(new SecureRandom().nextInt(999999));
        TokenVerification tokenVerification = new TokenVerification();
        tokenVerification.setToken(token);
        tokenVerification.setCreatedAt(LocalDateTime.now());
        tokenVerification.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        tokenVerification.setDriver(savedDriver);
        tokenVerificationRepository.save(tokenVerification);
        emailSender.send(savedDriver.getEmailAddress(),emailSender.buildEmail(savedDriver.getFirstName(),token)) ;

        DriversResponse driversResponse = new DriversResponse();
        driversResponse.setMessage("driver created");
        driversResponse.setStatusCode(201);

        return driversResponse;
    }

    @Override
    public String login(DriversLoginRequest driversLoginRequest) {
        Driver savedDriver = driverRepository.findDriverByEmailAddress(driversLoginRequest.getEmailAddress())
                .orElseThrow(()->new  RuntimeException("incorrect login details"));
        if(!savedDriver.isEnable())return "verify your account with otp";
        if(BCrypt.checkpw(driversLoginRequest.getPassword(),savedDriver.getPassword()))
           return  "login successful";

       return "login failed";

    }

    @Override
    public void enableDriver(Driver driver) {
        driver.setEnable(true);
        driverRepository.save(driver);

    }
    @Override
    public  Driver findDriver(String emailAddress){
        Driver findDriver = driverRepository.findDriverByEmailAddress(emailAddress)
                .orElseThrow(()-> new RuntimeException("email does not exist"));
        return findDriver;

    }

    @Override
    public CarRegistrationResponse carRegister(CarRegistrationRequest carRegistrationRequest) {
        Optional<Driver> driver = driverRepository.findDriverByEmailAddress(carRegistrationRequest.getEmailAddress().toLowerCase());
        if (driver.isPresent()){
            Optional<Car> savedCar = carReposiory.findByDriverId(driver.get().getId());
            if (savedCar.isEmpty()){
                Car car =new  Car();
                car.setCarColour(carRegistrationRequest.getCarColour());
                car.setCarModel(carRegistrationRequest.getCarModel());
                car.setCarNumber(carRegistrationRequest.getCarNumber());
                car.setDriver(driver.get());
                carReposiory.save(car);

                CarRegistrationResponse carRegistrationResponse = new CarRegistrationResponse();
                carRegistrationResponse.setMessage("car registered successfully");

                return carRegistrationResponse;

            }
            throw new IllegalStateException("You can't register more than a car !!!");
        }
        throw new IllegalStateException("Invalid Driver details");


    }

    @Override
    public Driver getDriver(String location) {

        List<Driver> drivers = driverRepository.findByLocation(location);
        List<Driver> availableDriver = new ArrayList<>();
        for (Driver driver: drivers) {
            if (driver.getDriverStatus().equals(DriverStatus.AVAILABLE)){
                driver.setDriverStatus(DriverStatus.ON_A_TRIP);
                availableDriver.add(driver);
            }
        }
        if (!availableDriver.isEmpty()){
            SecureRandom random = new SecureRandom();
            return availableDriver.get(random.nextInt(availableDriver.size()));

        }
        throw new IllegalStateException("No driver available at your location");
    }

    @Override
    public Car getCarByDriver(Driver driver) {
        return carReposiory.findByDriver(driver)
                .orElseThrow(()->new RuntimeException("car not found"));
    }


}
