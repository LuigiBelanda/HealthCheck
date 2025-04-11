package com.healtcheck.labeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthCheckApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthCheckApplication.class, args);
		System.out.println("Aplicação iniciada com sucesso!");
	}
}
