package com.oc.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/user")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public void createUser(@RequestBody User user) throws Exception { 
		
		logger.info("user: {}", user.toString());

		
		if (user.getLastname() != null && user.getFirstname() != null && user.getEmail() != null && user.getPassword() != null)
			userService.createUser(user);	
		else
			throw new Exception("All fields must be filled");
	}
	
}
