package com.easyride.urbanBusTransit.data.models.token;

import com.easyride.urbanBusTransit.data.models.token.tokenRequest.PassengerTokenRequest;
import com.easyride.urbanBusTransit.data.models.token.tokenRequest.PassengerVerifyTokenRequest;
import com.easyride.urbanBusTransit.data.models.token.tokenRequest.VerifyTokenRequest;
import com.easyride.urbanBusTransit.data.repositories.TokenVerificationRepository;
import com.easyride.urbanBusTransit.services.DriverService;
import com.easyride.urbanBusTransit.services.PassengerService;
import com.google.maps.errors.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class TokenVerificationService {
    @Autowired
    DriverService driverService;
    @Autowired
    PassengerService passengerService;


    @Autowired
    TokenVerificationRepository tokenVerificationRepository;


    public String verifyToken(VerifyTokenRequest verifyTokenRequest) {
        TokenVerification savedOtp = tokenVerificationRepository.findTokenVerificationByToken(verifyTokenRequest.getToken())
                .orElseThrow(() -> new RuntimeException("invalid token"));
        if (LocalDateTime.now().isAfter(savedOtp.getExpiredAt())) {
            return "token expired";
        }
        savedOtp.setConfirmAt(LocalDateTime.now());
        driverService.enableDriver(savedOtp.getDriver());
        tokenVerificationRepository.save(savedOtp);
        return "verified";


    }
    public String VerifyPassenger(PassengerVerifyTokenRequest passengerVerifyTokenRequest){
        TokenVerification foundPassenger = tokenVerificationRepository.findTokenVerificationByToken(passengerVerifyTokenRequest.getToken())
                .orElseThrow(() -> new RuntimeException("invalid token"));
        if (LocalDateTime.now().isAfter(foundPassenger.getExpiredAt())) {
            return "token expired";
        }
        foundPassenger.setConfirmAt(LocalDateTime.now());
        try {
            passengerService.enablePassenger(foundPassenger.getPassenger());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        tokenVerificationRepository.save(foundPassenger);
        return "verified";
    }


        public void deleteExpiredToken() {

        tokenVerificationRepository.deleteConfirmationTokensByExpiredAtBefore(LocalDateTime.now());


    }


}




