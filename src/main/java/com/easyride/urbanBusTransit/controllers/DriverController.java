package com.easyride.urbanBusTransit.controllers;

import com.easyride.urbanBusTransit.data.dtos.requests.CarRegistrationRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.CreateDriverRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.DriversLoginRequest;
import com.easyride.urbanBusTransit.data.models.token.tokenRequest.VerifyTokenRequest;
import com.easyride.urbanBusTransit.services.DriverService;
import com.easyride.urbanBusTransit.data.models.token.TokenVerificationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/driver")
@CrossOrigin(origins = "*")
public class DriverController {
    @Autowired
    private DriverService driverService;
    @Autowired
    TokenVerificationService tokenVerificationService;

@PostMapping("/createDriver")
    public  ResponseEntity<?>createDriver(@RequestBody CreateDriverRequest createDriverRequest) throws MessagingException {
        return new ResponseEntity<>(driverService.createDriver(createDriverRequest), HttpStatus.OK);
}

@PostMapping("/verifyToken")
public  ResponseEntity<?>verifyToken(@RequestBody VerifyTokenRequest verifyTokenRequest){
    return  new ResponseEntity<>(tokenVerificationService.verifyToken(verifyTokenRequest),HttpStatus.ACCEPTED);
}

@PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody DriversLoginRequest driversLoginRequest){
        return new ResponseEntity<>(driverService.login(driversLoginRequest),HttpStatus.ACCEPTED);
}
    @PostMapping("/carRegister")
    public  ResponseEntity<?>carRegister(@RequestBody CarRegistrationRequest carRegistrationRequest) {
        return new ResponseEntity<>(driverService.carRegister(carRegistrationRequest), HttpStatus.ACCEPTED);
    }
}
