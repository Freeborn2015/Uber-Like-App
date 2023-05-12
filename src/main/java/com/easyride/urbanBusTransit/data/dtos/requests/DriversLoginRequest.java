package com.easyride.urbanBusTransit.data.dtos.requests;

import lombok.Data;

@Data
public class DriversLoginRequest {
    private String password;
    private String emailAddress;
}
