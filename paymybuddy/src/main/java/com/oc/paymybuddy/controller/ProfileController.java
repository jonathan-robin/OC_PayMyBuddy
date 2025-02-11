package com.oc.paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "User Details: " + userDetails.getUsername();
    }
	
	@GetMapping("")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception { 
		
		logger.info("User Details: {} ", userDetails.getUsername());
		
		User user = userService.findUser(userDetails);

	    model.addAttribute("user", user);
    	return "profile";
    }
	
}
