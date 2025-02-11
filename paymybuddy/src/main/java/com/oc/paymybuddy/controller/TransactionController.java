package com.oc.paymybuddy.controller;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.oc.paymybuddy.repository.TransactionRepository;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private UserService userSvc;
	
	@Autowired
	private TransactionService transactionSvc;
	
	@Autowired
	private ViewController viewController;
	
	
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "User Details: " + userDetails.getUsername();
    }

	@PostMapping("")
	public String addTransaction(Model model, @ModelAttribute("connection") Transaction transaction, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		
		User user = userSvc.findUser(userDetails);
		
		transaction.setUserFrom(user.getId());
		transaction.setDate(new Date(System.currentTimeMillis()));
		
		if (transaction.getAmount() <= 0) {
			model.addAttribute("error", "Amount can't be lower than 0.");
			return viewController.showTransactions(model, userDetails);
		}
		
		/* even though it's not possible to have a connection with ourself 
		 * Double check to not be able to send money to ourself
		 * */
		if (transaction.getUserFrom() == transaction.getUserTo()) {
			model.addAttribute("error", "You're not supposed to have a connection with yourself.");
			return viewController.showTransactions(model, userDetails);
		}
	
		try {
			transactionSvc.createTransaction(transaction);	
			return viewController.showTransactions(model, userDetails);
		}
		catch (Exception e) { 
			model.addAttribute("error", e.getMessage());
			return viewController.showTransactions(model, userDetails);
		}
	}
	
}
