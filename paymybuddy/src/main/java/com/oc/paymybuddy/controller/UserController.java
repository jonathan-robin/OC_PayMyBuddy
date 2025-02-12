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
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
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
			
		    model.addAttribute("login", new User());
			return "login";
		}
		else {
			return "sign-in/signIn-error";
		}	
	}
	
}
