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
import com.oc.paymybuddy.repository.UserConnectionRepository;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;


@Controller
@RequestMapping("/transfer")
public class TransferController {

	Logger logger = LoggerFactory.getLogger(UserController.class);	
	
	@Autowired
	private UserConnectionService userConSvc;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;

    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "User Details: " + userDetails.getUsername();
    }
	
	@GetMapping("")
    public String transfer(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception { 
		
		logger.info("User Details: {} ", userDetails.getUsername());
		
		User user = userService.findUser(userDetails);
		List<UserConnection> userCons = userConSvc.getUserConnection(user);
		
	    List<User> users = new ArrayList<User>();
	    
	    for (UserConnection userCon: userCons) {
	    	User _user = userService.findUserById(userCon.getUserConnection().getId());
	    	users.add(_user);
	    	logger.info("userCon: {} ",userCon);
	    	logger.info("_user: {} ",_user);
	    }
	    
	    List<Transaction> transactions = transactionService.findTransactionByUserId(user.getId());
	    
	    for (Transaction trans: transactions) { 
	    	logger.info("transactions: {} ", trans);
	    }
	    

	    model.addAttribute("users", users);
    	model.addAttribute("transfer", model);
    	model.addAttribute("transactions", transactions);
    	model.addAttribute("connection", new Transaction());
    	return "transfer";
    }
	
}
