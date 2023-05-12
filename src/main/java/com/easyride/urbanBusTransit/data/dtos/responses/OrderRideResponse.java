package com.easyride.urbanBusTransit.data.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRideResponse {
    private String message;
    private LocalDateTime dateOfRide;
    private String driverName;
    private String phoneNumber;
    private String carModel;
    private String carNumber;
    private String carColor;
}
