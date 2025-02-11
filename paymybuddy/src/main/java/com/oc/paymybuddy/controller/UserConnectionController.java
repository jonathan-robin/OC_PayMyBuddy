package com.oc.paymybuddy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/user-connection")
public class UserConnectionController {
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private ViewController viewController;
	
	@Autowired
	private UserConnectionService userConSvc;
	
	
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "User Details: " + userDetails.getUsername();
    }

    @PostMapping
    public String addUserConnection(Model model, 
    		@ModelAttribute("email") String email, 
    		@AuthenticationPrincipal UserDetails userDetails) throws Exception {

		User userFrom;
		try {
			userFrom = userSvc.findUser(userDetails);
			User userTo = userSvc.findByEmail(email);
			
			if (userConSvc.checkIfUserConnectionTryToAddHimself(userFrom, userTo))
				throw new Exception("You are trying to add yourself, that's not allowed.");
			if (userConSvc.checkIfUserConnectionIsAlreadyExisting(userFrom, userTo))
				throw new Exception("User with email :" + userTo.getEmail() + " is already in your connections list !");
			
			userConSvc.addUserConnection(userFrom, userTo);
			
			return viewController.AddConnection(model, userDetails);
			
		} catch (Exception e) {
			log.warn("No User found for email adress: {} ", email);
			log.info("exception: {}",e);
			model.addAttribute("error", e.getMessage());
			return viewController.AddConnection(model, userDetails);
		}
    	
    }

}
