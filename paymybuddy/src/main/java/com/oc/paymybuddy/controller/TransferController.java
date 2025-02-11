package com.oc.paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.paymybuddy.dto.TransactionDto;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.repository.UserConnectionRepository;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("/transfer")
public class TransferController {

	Logger logger = LoggerFactory.getLogger(UserController.class);	
	
	@Autowired
	private UserConnectionService userConSvc;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserConnectionRepository userConRepo;
	
	@Autowired
	private TransactionService transactionService;

    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "User Details: " + userDetails.getUsername();
    }
	
	@GetMapping("")
    public String transfer(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception { 
		
		logger.info("User Details: {} ", userDetails.getUsername());
		logger.info("userDdetails: {}", userDetails);

		User user = userService.findUser(userDetails);
		List<UserConnection> userCons = userConSvc.getUserConnection(user);
		
	    List<User> users = new ArrayList<User>();
	    
	    for (UserConnection userCon: userCons) {
	    	Optional<User> _user = userService.findUserById(userCon.getUserConnection().getId());
	    	users.add(_user.get());
	    }
	    
	    List<Transaction> transactions = transactionService.findTransactionByUserId(user.getId());
	    List<TransactionDto> transactionsDto = new ArrayList<>(); 
	    /** parse transaction to DTO */
	    for (Transaction transaction: transactions) { 
	    	TransactionDto dto = new TransactionDto(); 
	    	dto.setId(transaction.getId());
	    	dto.setAmount(transaction.getAmount());
	    	dto.setDescription(transaction.getDescription());
	    	dto.setUserFrom(userService.findUserById(transaction.getUserFrom()).get()); 
	    	dto.setUserTo(userService.findUserById(transaction.getUserTo()).get()); 
	    	dto.setDescription(transaction.getDescription());
	    	dto.setDate(transaction.getDate().toString());
	    	transactionsDto.add(dto);
	    }
	    
	    log.info("users: {}", users);
	    log.info("model: {}", model);
	    log.info("transactionsDto: {}", transactionsDto);

	    model.addAttribute("users", users);
    	model.addAttribute("transfer", model);
    	model.addAttribute("transactions", transactionsDto);
    	model.addAttribute("connection", new Transaction());
    	return "transfer";
    }
	
	@GetMapping("/connections")
    public String AddConnection(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception { 
		
		User user = userService.findUser(userDetails);
		List<UserConnection> userConnections = userConRepo.findUserConnectionByUserId(user.getId());
		model.addAttribute("connections", userConnections);
		
		return "connections";

    }

}
