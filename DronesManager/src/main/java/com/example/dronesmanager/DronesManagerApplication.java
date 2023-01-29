package com.example.dronesmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronesManagerApplication.class, args);
	}

}
