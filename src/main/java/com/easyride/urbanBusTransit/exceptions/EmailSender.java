package com.easyride.urbanBusTransit.exceptions;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailSender {
    @Async
    void send(String to, String email) throws MessagingException;
    String buildEmail(String name, String link);
}
