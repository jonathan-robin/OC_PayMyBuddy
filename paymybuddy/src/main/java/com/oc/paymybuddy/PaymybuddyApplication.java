package com.oc.paymybuddy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication

public class PaymybuddyApplication {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserConnectionService userConService;
	
	@Autowired
	private TransactionService transactionService;

	Logger logger = LoggerFactory.getLogger(PaymybuddyApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}
	
	
	@PostConstruct
	public void init() { 
		try { 

			Iterable <UserConnection> cons = userConService.getUserConnection();
			
			for (UserConnection con: cons) {
				logger.info("{}", con.getUser());
			}
			
		}catch( Exception e) { 
			e.printStackTrace();
		}
	}
	
	

}
