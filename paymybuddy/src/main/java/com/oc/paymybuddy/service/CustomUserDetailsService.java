package com.oc.paymybuddy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.oc.paymybuddy.repository.UserRepository;	

@Service
public class CustomUserDetailsService implements UserDetailsService {

		Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
		
		private SecurityContextRepository securityContextRepository  = new HttpSessionSecurityContextRepository(); 
	
		@Autowired
		private UserRepository userRepo;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			logger.info("username : {}", username);
			com.oc.paymybuddy.model.User user = userRepo.findByEmail(username);
			logger.info("user in customUser: {}",user.toString());
			return new User(user.getEmail(), user.getPassword(), user.getRole());
		}

}
