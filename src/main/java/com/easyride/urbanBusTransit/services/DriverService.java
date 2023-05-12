package com.easyride.urbanBusTransit.services;

import com.easyride.urbanBusTransit.data.dtos.requests.CarRegistrationRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.CreateDriverRequest;
import com.easyride.urbanBusTransit.data.dtos.requests.DriversLoginRequest;
import com.easyride.urbanBusTransit.data.dtos.responses.CarRegistrationResponse;
import com.easyride.urbanBusTransit.data.dtos.responses.DriversResponse;
import com.easyride.urbanBusTransit.data.models.Car;
import com.easyride.urbanBusTransit.data.models.Driver;
import jakarta.mail.MessagingException;

public interface DriverService {
    DriversResponse createDriver(CreateDriverRequest createDriverRequest) throws MessagingException;
    String login(DriversLoginRequest driversLoginRequest);
    void   enableDriver (Driver driver);
    Driver findDriver(String emailAddress);
    CarRegistrationResponse carRegister(CarRegistrationRequest carRegistrationRequest);

    Driver getDriver(String location);
    Car getCarByDriver(Driver assignedDriver);



}
