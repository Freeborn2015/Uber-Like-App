package com.easyride.urbanBusTransit.services;

import com.easyride.urbanBusTransit.data.dtos.requests.CreatePassengerRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.OrderRideRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.PassengerLoginRequest;
import com.easyride.urbanBusTransit.data.dtos.responses.OrderRideResponse;
import com.easyride.urbanBusTransit.data.dtos.responses.PassengerResponse;
import com.easyride.urbanBusTransit.data.models.Car;
import com.easyride.urbanBusTransit.data.models.Driver;
import com.easyride.urbanBusTransit.data.models.Passenger;
import com.easyride.urbanBusTransit.data.models.Trip;
import com.easyride.urbanBusTransit.data.models.token.TokenVerification;
import com.easyride.urbanBusTransit.data.repositories.PassengerRepository;
import com.easyride.urbanBusTransit.data.repositories.TokenVerificationRepository;
import com.easyride.urbanBusTransit.data.repositories.TripRepository;
import com.easyride.urbanBusTransit.exceptions.EmailSender;
import com.easyride.urbanBusTransit.validators.Validator;

import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    TokenVerificationRepository tokenVerificationRepository;
    @Autowired
    EmailSender emailSender;
    @Autowired
    DriverService driverService;
    @Autowired
    TripRepository tripRepository;

    //private  final  String SECRET_KEY= System.getenv("API_KEY");

    @Override
    public PassengerResponse createPassenger(CreatePassengerRequest createPassengerRequest) throws MessagingException {
        if (!Validator.isValidEmailAddress(createPassengerRequest.getEmailAddress()))
            throw new RuntimeException(String.format("%s email is weak ", createPassengerRequest.getEmailAddress()));
        if (!Validator.isValidPassword(createPassengerRequest.getPassword()))
            throw new RuntimeException(String.format("%s password is weak please try again", createPassengerRequest.getPassword()));
        if (!Validator.isValidPhoneNumber(createPassengerRequest.getPhoneNumber()))
            throw new RuntimeException(String.format("%s number must be 11 digit", createPassengerRequest.getPhoneNumber()));
        Passenger passenger= new Passenger();
        if (passengerRepository.findPassengerByEmailAddress(createPassengerRequest.getEmailAddress().toLowerCase()).isPresent())
            throw new RuntimeException("email already exist");
        else
           passenger .setEmailAddress(createPassengerRequest.getEmailAddress().toLowerCase());

        passenger.setFirstName(createPassengerRequest.getFirstName());
        passenger.setLastName(createPassengerRequest.getLastName());
        passenger.setPhoneNumber(createPassengerRequest.getPhoneNumber());
        String password = BCrypt.hashpw(createPassengerRequest.getPassword(), BCrypt.gensalt());
        passenger.setPassword(password);
       Passenger savedPassenger =  passengerRepository.save(passenger);
        String token = new DecimalFormat("000000").format(new SecureRandom().nextInt(999999));
        TokenVerification tokenVerification = new TokenVerification();
        tokenVerification.setToken(token);
        tokenVerification.setCreatedAt(LocalDateTime.now());
        tokenVerification.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        tokenVerification.setPassenger(savedPassenger);
       tokenVerificationRepository.save(tokenVerification);
        emailSender.send(savedPassenger.getEmailAddress(),emailSender.buildEmail(savedPassenger.getFirstName(),token)) ;

        PassengerResponse passengerResponse = new PassengerResponse();
        passengerResponse.setMessage("user created successfully");
        passengerResponse.setStatusCode(201);

        return passengerResponse;
    }

    @Override
    public String login(PassengerLoginRequest passengerLoginRequest) {
        Passenger foundPassenger = passengerRepository.findPassengerByEmailAddress(passengerLoginRequest.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("email or password incorrect"));
        if(!foundPassenger.isEnable())return "verify your account with otp";
        if (BCrypt.checkpw(passengerLoginRequest.getPassword(),foundPassenger.getPassword()))
                return "login successful";

        return "login failed";

    }

//    @Override
//    public OrderRideResponse orderRide(OrderRideRequest orderRideRequest) {
//        Optional<Passenger> savedPassenger = passengerRepository.findDPassengerByEmailAddress(orderRideRequest.getEmailAddress().toLowerCase());
//        if (savedPassenger.isPresent()){
//            Driver assignedDriver = driverService.getDriver(orderRideRequest.getLocation());
//            Trip trip = new Trip();
//            trip.setPickUpAddress(orderRideRequest.getPickUpAddress());
//            trip.setDropOffAddress(orderRideRequest.getDropOffAddress());
//            trip.setLocation(orderRideRequest.getLocation());
//            trip.setPassenger(savedPassenger.get());
//            trip.setDriver(assignedDriver);
//           Trip savedTrip =  tripRepository.save(trip);
//           Car car = driverService.getCarByDriver(assignedDriver);
//           return getOrderTripResponse(assignedDriver,savedTrip,car);
//
//
//            //        GeoApiContext context = new GeoApiContext.Builder()
////
////                .apiKey(SECRET_KEY)
////                .build();
////        GeocodingResult[] results =  GeocodingApi.geocode(context,
////                "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
////       //DirectionsResult result = DirectionsApi.newRequest(context)
////        //GeolocationResult location = GeolocationApi.newRequest(context)
////
////        Gson gson = new GsonBuilder().setPrettyPrinting().create();
////        System.out.println(gson.toJson(results[0].addressComponents));
////        context.shutdown();
//        }
//        throw new RuntimeException("User does not exist");
//
//    }

    public OrderRideResponse orderRide(OrderRideRequest orderRideRequest) {
        Optional<Passenger> savedPassenger = passengerRepository.findPassengerByEmailAddress(orderRideRequest.getEmailAddress());
        if (savedPassenger.isPresent()) {
            Driver assignedDriver = driverService.getDriver(orderRideRequest.getLocation());
            if (assignedDriver == null) {
                throw new RuntimeException("No available driver in the requested location");
            }
            Trip trip = new Trip();
            trip.setPickUpAddress(orderRideRequest.getPickUpAddress());
            trip.setDropOffAddress(orderRideRequest.getDropOffAddress());
            trip.setLocation(orderRideRequest.getLocation());
            trip.setPassenger(savedPassenger.get());
            trip.setDriver(assignedDriver);
            Trip savedTrip = tripRepository.save(trip);
            Car car = driverService.getCarByDriver(assignedDriver);
            return getOrderTripResponse(assignedDriver, savedTrip, car);
        }
        throw new RuntimeException("Passenger does not exist");
    }


    @Override
    public void enablePassenger(Passenger passenger) {
        passenger.setEnable(true);
        passengerRepository.save(passenger);

    }
    @Override
    public  Passenger findPassenger(String emailAddress){
        Passenger findPassenger = passengerRepository.findDPassengerByEmailAddress(emailAddress)
                .orElseThrow(()-> new RuntimeException("email does not exist"));
        return findPassenger;

    }
    private OrderRideResponse getOrderTripResponse(Driver driver, Trip savedTrip, Car car){
        OrderRideResponse orderRideResponse = new OrderRideResponse();
        orderRideResponse.setCarColor(driver.getFirstName());
        orderRideResponse.setCarModel(driver.getLastName());
        orderRideResponse.setDateOfRide(savedTrip.getTime());
        orderRideResponse.setPhoneNumber(driver.getPhoneNumber());
        orderRideResponse.setCarModel(car.getCarModel());
        orderRideResponse.setCarNumber(car.getCarNumber());
        orderRideResponse.setCarColor(car.getCarColour());
        orderRideResponse.setMessage("You are connected to a driver");
       return orderRideResponse;

    }
}