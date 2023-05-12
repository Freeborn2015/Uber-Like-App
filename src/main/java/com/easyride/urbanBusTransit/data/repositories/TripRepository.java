package com.easyride.urbanBusTransit.data.repositories;

import com.easyride.urbanBusTransit.data.models.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TripRepository extends MongoRepository<Trip,String> {
    Optional<Trip> findTripById(String id);
}
