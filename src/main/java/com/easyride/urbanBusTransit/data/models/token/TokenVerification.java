package com.easyride.urbanBusTransit.data.models.token;

import com.easyride.urbanBusTransit.data.models.Driver;
import com.easyride.urbanBusTransit.data.models.Passenger;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document
@RequiredArgsConstructor
public class TokenVerification {
    @Id
    private String id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime confirmAt;
    private LocalDateTime expiredAt;
    @DBRef
    private Driver driver;
    @DBRef
    private Passenger passenger;

    public TokenVerification(String id, String token , LocalDateTime createdAt,LocalDateTime confirmAt, LocalDateTime expiredAt) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.confirmAt = confirmAt;
        this.expiredAt = expiredAt;
    }


}



