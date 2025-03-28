package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("model")
@SpringBootApplication
public class LyrifyWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyrifyWebApplication.class, args);
	}

}
