package com.easyride.urbanBusTransit.data.dtos.requests;

import lombok.Data;

@Data
public class CarRegistrationRequest {
    private String emailAddress;
    private String carNumber;
    private String carModel;
    private String carColour;

}
