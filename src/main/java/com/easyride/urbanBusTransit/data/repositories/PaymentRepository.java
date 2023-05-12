package com.easyride.urbanBusTransit.data.repositories;

import com.easyride.urbanBusTransit.data.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface PaymentRepository extends MongoRepository<Payment,String> {
}
