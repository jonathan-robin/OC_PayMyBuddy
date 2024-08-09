package com.oc.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepository transactionRepo; 
	
	public Iterable<Transaction> getTransactions(){ 
		return transactionRepo.findAll();
	}
	
}
