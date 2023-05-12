package com.easyride.urbanBusTransit.data.dtos.requests;

import lombok.Data;

@Data
public class CreatePassengerRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String password;
}
