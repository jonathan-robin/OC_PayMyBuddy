package com.oc.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.paymybuddy.controller.TransactionController;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.model.UserConnectionId;
import com.oc.paymybuddy.repository.UserConnectionRepository;

@Service
public class UserConnectionService {

	private static Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	UserConnectionRepository userConRepo;
	
	public List<UserConnection> getUserConnection(User user){ 
		return userConRepo.findUserConnectionByUserId(user.getId());
	}
	
	public UserConnection addUserConnection(User user, User userTo) {
		
		UserConnection userCon = new UserConnection(user, userTo);
		userConRepo.save(userCon); 
		
		return userCon;
		
	}
	
	public boolean checkIfUserConnectionTryToAddHimself(User userFrom, User userTo) {
		return userFrom.getId() == userTo.getId();
	}
	
	public boolean checkIfUserConnectionIsAlreadyExisting(User userFrom, User userTo) {
		return userConRepo.findUserConnectionByUserFromIdAndUserToId(userFrom.getId(), userTo.getId()).isPresent();
	}
	
	
}
