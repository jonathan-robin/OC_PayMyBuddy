package com.oc.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oc.paymybuddy.PaymybuddyApplication;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CustomUserDetailsService customUserSvc;
	
	@Autowired
	private UserConnectionService userConSvc;

	
	Logger logger = LoggerFactory.getLogger(PaymybuddyApplication.class);

	
	public Optional<User> getUserByEmail(String email) { 
		return userRepo.findByEmail(email);
	}
	
	public User createUser(User user) throws Exception { 
		Optional<User> _user = userRepo.findByEmail(user.getEmail()); 
		if (_user.isPresent())
			throw new Exception("Email already used!"); 
		
		else { 
			
			User newUser = new User(); 
			
			newUser.setEmail(user.getEmail());
			newUser.setFirstname(user.getFirstname());
			newUser.setLastname(user.getLastname()); 
			newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			newUser.setUsername(user.getUsername());
			newUser.setBalance(0D);
			newUser.setRole("user");
			userRepo.save(newUser);
			customUserSvc.loadUserByUsername(newUser.getEmail());
			return newUser;
			
		}
	}
	
	public User findUser(UserDetails userDetails) throws Exception { 
		
		Optional<User> user = userRepo.findByEmail(userDetails.getUsername());

		if (user.isPresent())
			return user.get();
		
		throw new Exception("User can't be found");
		
	}
	
	public Optional<User> findUserById(Integer id)  throws Exception { 
		
		Optional<User> user = userRepo.findById(id);

		if (user.isPresent())
			return user;
		
		throw new Exception("User can't be found");
	}
	
	public List<User> findAll(){ 
		
		return userRepo.findAll();
		
	}
	
	public User findByEmail(String email) throws Exception{ 
		
		Optional<User> user = userRepo.findByEmail(email);
		
		if (user.isPresent()) 
    		return user.get();
    	
    	throw new Exception("User can't be found with email: " + email);
	
	}
	
	public List<User> getAllConnectedUser(User user) throws Exception{ 
		List<UserConnection> userCons = userConSvc.getUserConnection(user);
		return userCons.stream().map(userCon -> {
			try {
				return findUserById(userCon.getUserConnection().getId()).get();
			} catch (Exception e) {
				return null;
			}
		}).toList();
	}
	

	
	
	
	
}
