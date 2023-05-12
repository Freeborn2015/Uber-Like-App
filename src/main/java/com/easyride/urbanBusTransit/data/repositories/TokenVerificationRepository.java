package com.easyride.urbanBusTransit.data.repositories;

import com.easyride.urbanBusTransit.data.models.token.TokenVerification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenVerificationRepository extends MongoRepository<TokenVerification, String> {
    Optional<TokenVerification>findTokenVerificationByToken(String token);

    void deleteConfirmationTokensByExpiredAtBefore(LocalDateTime currentTime);

}
