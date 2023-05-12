package com.easyride.urbanBusTransit.services;

import com.easyride.urbanBusTransit.data.dtos.requests.CreatePassengerRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.OrderRideRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.PassengerLoginRequest;
import com.easyride.urbanBusTransit.data.dtos.responses.OrderRideResponse;
import com.easyride.urbanBusTransit.data.dtos.responses.PassengerResponse;
import com.easyride.urbanBusTransit.data.models.Passenger;
import com.google.maps.errors.ApiException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public interface PassengerService {
    PassengerResponse createPassenger(CreatePassengerRequest createPassengerRequest) throws MessagingException;
    void   enablePassenger (Passenger passenger) throws IOException, InterruptedException, ApiException;
    Passenger findPassenger(String emailAddress);
    String login(PassengerLoginRequest passengerLoginRequest);
    OrderRideResponse orderRide(OrderRideRequest orderRideRequest) throws IOException, InterruptedException, ApiException;
}
