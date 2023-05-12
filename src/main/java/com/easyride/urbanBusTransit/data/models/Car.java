package com.easyride.urbanBusTransit.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @org.springframework.data.annotation.Id
    private long Id;
    private String carNumber;
    private String carModel;
    private String carColour;
    @DBRef
    private Driver driver;
}

