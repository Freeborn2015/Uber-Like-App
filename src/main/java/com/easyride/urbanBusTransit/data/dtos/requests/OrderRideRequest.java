package com.easyride.urbanBusTransit.data.dtos.requests;

import lombok.Data;

@Data
public class OrderRideRequest {
    private String pickUpAddress;
    private String dropOffAddress;
    private String emailAddress;
    private String location;

}
