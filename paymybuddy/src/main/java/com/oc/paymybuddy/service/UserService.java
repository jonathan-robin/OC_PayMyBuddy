package com.oc.paymybuddy.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oc.paymybuddy.PaymybuddyApplication;
import com.oc.paymybuddy.configuration.AuthConfig;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CustomUserDetailsService customUserSvc;

	
	Logger logger = LoggerFactory.getLogger(PaymybuddyApplication.class);

	
	public User getUserByEmail(String email) { 
		return userRepo.findByEmail(email);
	}
	
	public User createUser(User user) throws Exception { 
		
		logger.info("createUser: {}", user.toString());
		User _user = userRepo.findByEmail(user.getEmail()); 
		if (_user != null)
			throw new Exception("Email already used!"); 
		
		else { 
			
			User newUser = new User(); 
			
			newUser.setEmail(user.getEmail());
			newUser.setFirstname(user.getFirstname());
			newUser.setLastname(user.getLastname()); 
			newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			newUser.setUsername(user.getUsername());
			
			newUser.setRole("admin");
			
			logger.info("newUser: {}", newUser);
			userRepo.save(newUser);
			
			customUserSvc.loadUserByUsername(newUser.getUsername());
			
			return newUser;
			
		}
	}
	
	public User findUser(UserDetails userDetails) throws Exception { 
		
		User user = userRepo.findByEmail(userDetails.getUsername());

		if (user != null)
			return user;
		
		throw new Exception("user can't be found");
		
	}
	
	public User findUserById(Integer id)  throws Exception { 
		
		Optional<User> user = userRepo.findById(id);

		if (user.isPresent())
			return user.get();
		
		throw new Exception("user can't be found");
	}
	
	
	
	
}
