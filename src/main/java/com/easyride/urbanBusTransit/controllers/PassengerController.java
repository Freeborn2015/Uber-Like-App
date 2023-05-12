package com.easyride.urbanBusTransit.controllers;

import com.easyride.urbanBusTransit.data.dtos.requests.CreatePassengerRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.OrderRideRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.PassengerLoginRequest;
import com.easyride.urbanBusTransit.data.models.token.TokenVerificationService;
import com.easyride.urbanBusTransit.data.models.token.tokenRequest.PassengerVerifyTokenRequest;
import com.easyride.urbanBusTransit.services.PassengerService;
import com.google.maps.errors.ApiException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/passenger")
@CrossOrigin(origins = "*")

public class PassengerController {
    @Autowired
    private PassengerService passengerService;
    @Autowired
    TokenVerificationService tokenVerificationService;

    @PostMapping("/createPassenger")
    public ResponseEntity<?>createPassenger(@RequestBody CreatePassengerRequest createPassengerRequest) throws MessagingException {
        return new ResponseEntity<>(passengerService.createPassenger(createPassengerRequest), HttpStatus.CREATED);
    }

    @PostMapping("/verifypassenger")
    public ResponseEntity<?>verifyPassenger(@RequestBody PassengerVerifyTokenRequest passengerVerifyTokenRequest){
        return  new ResponseEntity<>(tokenVerificationService.VerifyPassenger(passengerVerifyTokenRequest),HttpStatus.ACCEPTED);
    }
    @PostMapping("/login")
    public  ResponseEntity<?>login(@RequestBody PassengerLoginRequest passengerLoginRequest){
        return  new ResponseEntity<>(passengerService.login(passengerLoginRequest),HttpStatus.ACCEPTED);
    }
    @PostMapping("/order")
    public  ResponseEntity<?>orderRide(@RequestBody OrderRideRequest orderRideRequest) {
        try {
            return  new ResponseEntity<>(passengerService.orderRide(orderRideRequest),HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }


}
