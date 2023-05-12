package com.easyride.urbanBusTransit.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@Document
public class Trip {
    @Id
    private String id;
    private String pickUpAddress;
    private String dropOffAddress;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time = LocalDateTime.now();
    @DBRef
    private Driver driver;
    @DBRef
    private Passenger passenger;
}
