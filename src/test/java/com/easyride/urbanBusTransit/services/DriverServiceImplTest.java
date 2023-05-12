package com.easyride.urbanBusTransit.services;

import com.easyride.urbanBusTransit.data.dtos.requests.CarRegistrationRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.CreateDriverRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.DriversLoginRequest;
import com.easyride.urbanBusTransit.data.dtos.responses.CarRegistrationResponse;
import com.easyride.urbanBusTransit.data.dtos.responses.DriverLoginResponse;
import com.easyride.urbanBusTransit.data.dtos.responses.DriversResponse;
import com.easyride.urbanBusTransit.data.models.token.TokenVerificationService;
import com.easyride.urbanBusTransit.data.models.token.tokenRequest.VerifyTokenRequest;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class DriverServiceImplTest {
    @Autowired
    private DriverService driverService;
    @Autowired
    TokenVerificationService tokenVerificationService;
    private CreateDriverRequest createDriverRequest;

    @BeforeEach
    void setUp() {
        createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setFirstName("Blackstars");
        createDriverRequest.setLastName("cherry");
        createDriverRequest.setEmailAddress("oluwafemi@gmail.com");
        createDriverRequest.setDriversLicense("FJK545KJ");
        createDriverRequest.setPhoneNumber("09087654321");
        createDriverRequest.setPassword("planeFive5*%");
        createDriverRequest.setLocation("Yaba-Tech");

    }
    @Test void  createDriver_Test() throws MessagingException {
        DriversResponse driversResponse = driverService.createDriver(createDriverRequest);
        assertNotNull(driversResponse);
        assertEquals("driver created", driversResponse.getMessage());
    }
    @Test void  driverTokenVerification_Test(){
        VerifyTokenRequest verifyTokenRequest = new VerifyTokenRequest();
        verifyTokenRequest.setToken("590097");
        verifyTokenRequest.setEmailAddress("oluwafemi@gmail.com");
         tokenVerificationService.verifyToken(verifyTokenRequest);

    }

    @Test void  driverLogin_Test(){
        DriversLoginRequest driversLoginRequest = new DriversLoginRequest();
        driversLoginRequest.setEmailAddress("oluwafemi@gmail.com");
        driversLoginRequest.setPassword("planeFive5*%");

        String driverLoginResponse = driverService.login(driversLoginRequest);
        assertNotNull(driverLoginResponse);
        assertEquals("login successful",driverLoginResponse.trim());

    }
    @Test void carRegistration_Test(){
        CarRegistrationRequest carRegistrationRequest = new CarRegistrationRequest();
        carRegistrationRequest.setCarColour("Red");
        carRegistrationRequest.setCarModel("Venza");
        carRegistrationRequest.setCarNumber("fjk543JA");
        carRegistrationRequest.setEmailAddress("oluwafemi@gmail.com");

        CarRegistrationResponse carRegistrationResponse = driverService.carRegister(carRegistrationRequest);
        assertNotNull(carRegistrationResponse);
        assertEquals("car registered successfully", carRegistrationResponse.getMessage());
    }

    @Test void driverCanAcceptRide_Test(){


    }

}