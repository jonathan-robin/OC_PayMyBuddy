package com.oc.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {
	
	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
	
	@Autowired
	private UserService userService;

	@PostMapping("")
	public String addUser(@ModelAttribute("signIn") User user, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception { 
				
		if (user.getLastname() != null && user.getFirstname() != null && user.getEmail() != null && user.getPassword() != null) {
			User newUser = userService.createUser(user);
			
			if (newUser == null)
				throw new Exception("Error when creating new user");

			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getRole());
			SecurityContext context = SecurityContextHolder.createEmptyContext();
		    context.setAuthentication(authentication); 
		    SecurityContextHolder.setContext(context);
		    securityContextRepository.saveContext(context, request, response); 
			
		    return "sign-in/signed";
			
		}
		else {
			return "sign-in";
		}	
	}
	
}
