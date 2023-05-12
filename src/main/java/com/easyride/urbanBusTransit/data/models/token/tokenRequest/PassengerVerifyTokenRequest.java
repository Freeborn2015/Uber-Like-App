package com.easyride.urbanBusTransit.data.models.token.tokenRequest;

import lombok.Data;

@Data
public class PassengerVerifyTokenRequest {
    private String token;
    private String message;
}
