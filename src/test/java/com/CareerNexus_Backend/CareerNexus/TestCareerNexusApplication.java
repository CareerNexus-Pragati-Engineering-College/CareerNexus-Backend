package com.CareerNexus_Backend.CareerNexus;

import org.springframework.boot.SpringApplication;

public class TestCareerNexusApplication {

	public static void main(String[] args) {
		SpringApplication.from(CareerNexusApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
