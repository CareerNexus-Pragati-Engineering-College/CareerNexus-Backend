package com.CareerNexus_Backend.CareerNexus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CareerNexusApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerNexusApplication.class, args);
	}

}
