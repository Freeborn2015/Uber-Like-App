package com.easyride.urbanBusTransit.data.repositories;

import com.easyride.urbanBusTransit.data.models.Car;
import com.easyride.urbanBusTransit.data.models.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarReposiory extends MongoRepository<Car, String> {
    Optional<Car> findByDriverId(String id);


    Optional<Car> findByDriver(Driver driver);
}
