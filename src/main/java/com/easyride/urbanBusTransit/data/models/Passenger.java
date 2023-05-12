package com.easyride.urbanBusTransit.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Passenger {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private boolean isEnable;
    private  ArrayList<Integer>currentLocation = new ArrayList<>();
    private List<Payment> listOfPayment = new ArrayList<>();

}
