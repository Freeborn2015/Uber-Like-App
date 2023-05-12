package com.easyride.urbanBusTransit.data.models;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Location {
    private String locationId;
    private String latitude;
    private String longitude;



    }

