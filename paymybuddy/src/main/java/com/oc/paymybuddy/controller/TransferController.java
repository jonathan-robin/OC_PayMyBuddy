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
//@RequestMapping("/transfer")
public class TransferController {
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private UserConnectionRepository userConRepo;
//	
//	@Autowired
//	private TransactionService transactionService;
//
//    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
//        return "User Details: " + userDetails.getUsername();
//    }
//	
//	

}
