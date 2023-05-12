package com.easyride.urbanBusTransit.data.dtos.requests;

import com.easyride.urbanBusTransit.data.models.Location;
import lombok.Data;

@Data
public class CreateDriverRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private String driversLicense;
    private String location;

}
