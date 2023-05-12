package com.easyride.urbanBusTransit.services;

import com.easyride.urbanBusTransit.data.dtos.requests.CarRegistrationRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.CreatePassengerRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.OrderRideRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.PassengerLoginRequest;
import com.easyride.urbanBusTransit.data.dtos.responses.OrderRideResponse;
import com.easyride.urbanBusTransit.data.dtos.responses.PassengerLoginResponse;
import com.easyride.urbanBusTransit.data.dtos.responses.PassengerResponse;
import com.easyride.urbanBusTransit.data.models.token.TokenVerificationService;
import com.easyride.urbanBusTransit.data.models.token.tokenRequest.PassengerTokenRequest;
import com.easyride.urbanBusTransit.data.models.token.tokenRequest.PassengerVerifyTokenRequest;
import jakarta.mail.MessagingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PassengerServiceImplTest {
    @Autowired
    private PassengerService passengerService;
    @Autowired
    TokenVerificationService tokenVerificationService;
    PassengerVerifyTokenRequest passengerVerifyTokenRequest;
    private CreatePassengerRequest createPassengerRequest;

    @BeforeEach
    void setUp() {
        createPassengerRequest = new CreatePassengerRequest();
        createPassengerRequest.setFirstName("Franklin");
        createPassengerRequest.setLastName("Edward");
        createPassengerRequest.setEmailAddress("Landimark57@gmail.com");
        createPassengerRequest.setPassword("Obessey2726*%");
        createPassengerRequest.setPhoneNumber("90987563425");

    }
    @Test void createPassengerRequest_Test() throws MessagingException {
        PassengerResponse passengerResponse = passengerService.createPassenger(createPassengerRequest);
        assertNotNull(passengerResponse);
        assertEquals("user created successfully",passengerResponse.getMessage());

    }

    @Test void  passengerVerificationRequest_Test(){
     passengerVerifyTokenRequest = new PassengerVerifyTokenRequest();
     passengerVerifyTokenRequest.setToken("197086");
     tokenVerificationService.VerifyPassenger(passengerVerifyTokenRequest);



    }
    @Test void thatPassengerCanLogin_Test(){
        PassengerLoginRequest passengerLoginRequest = new PassengerLoginRequest();
        passengerLoginRequest.setPassword("Obessey2726*%");
        passengerLoginRequest.setEmailAddress("Landimark57@gmail.com");

        String passengerLoginResponse = passengerService.login(passengerLoginRequest);
        assertNotNull(passengerLoginResponse);
        assertEquals("login successful",passengerLoginResponse.trim());


    }
    @SneakyThrows
    @Test void passengerOrderRide_Test(){
        OrderRideRequest orderRideRequest = new OrderRideRequest();
        orderRideRequest.setPickUpAddress("herbert marculey way, yaba");
        orderRideRequest.setDropOffAddress("orsborne way, ikoyi");
        orderRideRequest.setLocation("Yaba-Tech");
        orderRideRequest.setEmailAddress("Landimark57@gmail.com");
        OrderRideResponse orderRideResponse =passengerService.orderRide(orderRideRequest);
        assertNotNull(orderRideResponse);
        assertEquals("You are connected to a driver",orderRideResponse.getMessage());
    }

}