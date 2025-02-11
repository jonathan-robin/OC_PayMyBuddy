package com.oc.paymybuddy.service;

import java.util.Optional;

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
	
		@Autowired
		private UserRepository userRepo;

		@Override
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			
			Optional<com.oc.paymybuddy.model.User> user = userRepo.findByEmail(email);

			if (user != null && user.isPresent()) {
				com.oc.paymybuddy.model.User _user = user.get();
				return new User(_user.getEmail(), _user.getPassword(), _user.getRole());
			}
			else {
				throw new UsernameNotFoundException("User not found.");
			}
		}

}
