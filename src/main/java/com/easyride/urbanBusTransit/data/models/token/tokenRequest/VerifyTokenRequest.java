package com.easyride.urbanBusTransit.data.models.token.tokenRequest;

import lombok.Data;

@Data
public class VerifyTokenRequest {
    private String token;
    private String emailAddress;

}
