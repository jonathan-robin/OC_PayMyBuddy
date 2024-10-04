package com.oc.paymybuddy.service;

import java.math.BigInteger;
import java.util.List;

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
	
	public List<Transaction> findTransactionByUserId(Integer userId){ 
	
		return transactionRepo.findTransactionByUserFrom(userId);
		
	}
	
	public Transaction createTransaction(Transaction transaction) { 
		return transactionRepo.save(transaction);
	}
	
	
	
}
