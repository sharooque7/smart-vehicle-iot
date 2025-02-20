package com.ainzson.smartvehiclemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartVehicleManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartVehicleManagementApplication.class, args);
	}

}
