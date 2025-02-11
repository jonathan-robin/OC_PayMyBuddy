package com.oc.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.oc.paymybuddy.controller.TransactionController;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.model.UserConnectionId;
import com.oc.paymybuddy.repository.UserConnectionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserConnectionService {

	@Autowired
	private UserConnectionRepository userConRepo;
	
	public List<UserConnection> getUserConnection(User user){ 
		return userConRepo.findUserConnectionByUserId(user.getId());
	}
	
	public UserConnection addUserConnection(User user, User userTo) {
		UserConnection userCon = new UserConnection(user, userTo);
		userConRepo.save(userCon); 
		
		return userCon;
	}
	
	public Boolean checkIfUserConnectionTryToAddHimself(User userFrom, User userTo) {
		return userFrom.getId() == userTo.getId();
	}
	
	public Boolean checkIfUserConnectionIsAlreadyExisting(User userFrom, User userTo) {
		return userConRepo.findUserConnectionByUserFromIdAndUserToId(userFrom.getId(), userTo.getId()).isPresent();
	}

	
	
}
