package com.easyride.urbanBusTransit.data.models;

import com.easyride.urbanBusTransit.data.models.enumm.PaymentType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document
@Data
public class Payment {
    @Id
    private String id;
    private PaymentType paymentType;
    private BigInteger amount;
    @DBRef
    private Passenger passenger;
    @DBRef
    private Driver driver;


}
