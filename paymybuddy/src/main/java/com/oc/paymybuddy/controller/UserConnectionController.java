package com.oc.paymybuddy.controller;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;

@Controller
@RequestMapping("/user-connection")
public class UserConnectionController {

private static Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private TransactionService transactionSvc;
	
	@Autowired
	private TransferController transferController;
	
	@Autowired
	private UserConnectionService userConSvc;
	
	
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "User Details: " + userDetails.getUsername();
    }

    @PostMapping
    public String addUserConnection(Model model, 
    		@ModelAttribute("email") String email, 
    		@AuthenticationPrincipal UserDetails userDetails) throws Exception {
    	
    	logger.info("email: {}", email);
		User userFrom;
		try {
			userFrom = userSvc.findUser(userDetails);
			User userTo = userSvc.findByEmail(email);
			logger.info("userFrom: {}", userFrom);
			logger.info("userTo: {}", userTo);
			userConSvc.addUserConnection(userFrom, userTo);
			
			return transferController.AddConnection(model, userDetails);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
    	
    }

}
