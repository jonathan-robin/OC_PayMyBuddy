package com.oc.paymybuddy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class PaymybuddyApplication {

	Logger logger = LoggerFactory.getLogger(PaymybuddyApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}
	
	
	@PostConstruct
	public void init() { 

	}
	
	

}
