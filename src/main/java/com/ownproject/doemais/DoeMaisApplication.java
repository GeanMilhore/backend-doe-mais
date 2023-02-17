package com.ownproject.doemais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class DoeMaisApplication {
	public static void main(String[] args) {
		SpringApplication.run(DoeMaisApplication.class, args);
	}

}
