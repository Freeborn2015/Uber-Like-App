package com.easyride.urbanBusTransit.data.models;


import com.easyride.urbanBusTransit.data.models.enumm.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Driver{
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private String driversLicense;
    private String ratings;
    private  boolean isEnable;
    private String location;
    private DriverStatus driverStatus;




}
