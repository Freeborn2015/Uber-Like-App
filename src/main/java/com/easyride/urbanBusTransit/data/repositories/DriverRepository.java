package com.easyride.urbanBusTransit.data.repositories;

import com.easyride.urbanBusTransit.data.models.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface DriverRepository extends MongoRepository<Driver,String> {
    Optional<Driver> findDriverByEmailAddress(String emailAddress);
    List<Driver> findByLocation(String location);

}
