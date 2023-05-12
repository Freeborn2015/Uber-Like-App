package com.easyride.urbanBusTransit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.mapping.Encrypted;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UrbanBusTransitApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbanBusTransitApplication.class, args);
	}

}
