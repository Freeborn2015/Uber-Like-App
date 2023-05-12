package com.easyride.urbanBusTransit.data.repositories;

import com.easyride.urbanBusTransit.data.models.Passenger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PassengerRepository extends MongoRepository<Passenger, String> {

    Optional<Passenger> findPassengerByEmailAddress(String emailAddress);

    Optional<Passenger> findDPassengerByEmailAddress(String emailAddress);

}
