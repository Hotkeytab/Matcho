package com.example.Matcho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Matcho")

public class MatchoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchoApplication.class, args);
	}

}
