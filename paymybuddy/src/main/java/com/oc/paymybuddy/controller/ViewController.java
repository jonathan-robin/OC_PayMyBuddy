package com.oc.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.oc.paymybuddy.dto.TransactionDto;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ViewController {
	
	@Value("${spring.application.name}")
    String appName;
	
    /**
     * Retrieves the authenticated user's details.
     *
     * @param userDetails the authenticated user's details
     * @return a string containing the user's username
     */
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "User Details: " + userDetails.getUsername();
    }

	@Autowired
	private TransactionService transactionService; 

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserConnectionService userConService;
	
	@GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    	model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/contact") 
    public String contact(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    	model.addAttribute("appName", appName);
        return "contact";
    }
	
	/************************************
	 *        LOGIN RELATED VIEW
	 ************************************
	 */
	@GetMapping("/login")
    public String loginView(Model model) {
    	model.addAttribute("login", new User());
        return "login";
    }

    @PostMapping("/login")
    public String logged(@ModelAttribute("login") User login) {
        return "home";
    }
    
    @GetMapping("/login/error/")
    public String loginError(@ModelAttribute("login") User login) {
        return "login-error";
    }
    
	/************************************
	 *        PROFILE RELATED VIEW
	 ************************************
	 */
	@GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception { 		
		User user = userService.findUser(userDetails);

	    model.addAttribute("user", user);
    	return "profile";
    }
	
	
	/************************************
	 *        SIGN IN RELATED VIEW
	 ************************************
	 */
	@GetMapping("/sign-in")
    public String signIn(Model model, @AuthenticationPrincipal UserDetails userDetails) { 
    	model.addAttribute("signIn", new User());
    	return "sign-in/signIn";
    }
  
    @GetMapping("/sign-in/signed")
    public String showSignedPage(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Call to /sign-in/signed");
        return "sign-in/signed";
    }
	
	@GetMapping("sign-in/signIn-error")
	public String showSignedError(@AuthenticationPrincipal UserDetails userDetails) { 
		return "sign-in/signIn-error";
	}
	
	/************************************
	 *        TRANSFER RELATED VIEW
	 ************************************
	 */
	@GetMapping("/transfer")
    public String showTransactions(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception { 
		
		User user = userService.findUser(userDetails);
		
		/* all the connection for the user */
	    List<User> users = userService.getAllConnectedUser(user);
	    List<Transaction> transactions = transactionService.findTransactionByUserId(user.getId());
	    List<TransactionDto> transactionsDto = transactionService.toDto(transactions);

	    model.addAttribute("users", users);
    	model.addAttribute("transfer", model);
    	model.addAttribute("transactions", transactionsDto);
    	model.addAttribute("connection", new Transaction());
    	return "transfer";
    }
	

	@GetMapping("/transfer/connections")
    public String showConnections(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception { 

		model.addAttribute("connections", userConService.getUserConnection(userService.findUser(userDetails)));
		return "connections";

    }

	
	
}
