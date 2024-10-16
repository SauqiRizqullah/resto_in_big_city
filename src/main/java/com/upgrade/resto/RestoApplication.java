package com.upgrade.resto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.upgrade.resto.entity")
public class RestoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestoApplication.class, args);
	}

}
