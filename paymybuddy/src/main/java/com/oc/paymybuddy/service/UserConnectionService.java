package com.oc.paymybuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.repository.UserConnectionRepository;

@Service
public class UserConnectionService {

	@Autowired
	UserConnectionRepository userConRepo;
	
	public List<UserConnection> getUserConnection(User user){ 
		return userConRepo.findUserConnectionByUserId(user.getId());
	}
	
}
