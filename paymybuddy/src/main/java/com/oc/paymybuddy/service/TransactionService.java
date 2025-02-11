package com.oc.paymybuddy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oc.paymybuddy.dto.TransactionDto;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {
	
	Logger logger = LoggerFactory.getLogger(TransactionService.class);	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionRepository transactionRepo; 
	
	public Iterable<Transaction> getTransactions(){ 
		return transactionRepo.findAll();
	}
	
	public List<Transaction> findTransactionByUserId(Integer userId){ 
	
		return transactionRepo.findTransactionByUserFrom(userId);
		
	}
	
	@Transactional
	public Transaction createTransaction(Transaction transaction) throws Exception { 
		
		Optional<User> optUserFrom = userService.findUserById(transaction.getUserFrom());
		Optional<User> optUserTo = userService.findUserById(transaction.getUserTo());
		
		if (!optUserFrom.isPresent() || !optUserTo.isPresent()) {
			throw new Exception("User can't be found");
		}
		
		User userFrom = optUserFrom.get();
		User userTo = optUserTo.get();
		Double amount = transaction.getAmount();
		Double balance = userFrom.getBalance();
		
		if (transaction.getAmount() > balance) {
			logger.warn("User {} try to make a transaction of {} with unsufficient balance: {}", userFrom.getUsername(), transaction.getAmount(), balance);
		    throw new Exception("Not enough money to make the transaction!" + System.lineSeparator() + "Your balance is " + balance);			
		}
		
		userTo.setBalance(amount + userTo.getBalance());
		userFrom.setBalance(userFrom.getBalance() - amount);
				
		return transactionRepo.save(transaction);
	}
	
	public TransactionDto toDto(Transaction transaction) throws Exception { 
		
    	TransactionDto dto = new TransactionDto(); 
    	dto.setId(transaction.getId());
    	dto.setAmount(transaction.getAmount());
    	dto.setDescription(transaction.getDescription());
    	dto.setUserFrom(userService.findUserById(transaction.getUserFrom()).get()); 
    	dto.setUserTo(userService.findUserById(transaction.getUserTo()).get()); 
    	dto.setDescription(transaction.getDescription());
    	dto.setDate(transaction.getDate().toString());
    	return dto;
    	

    }
	
	public List<TransactionDto> toDto(List<Transaction> transactions) throws Exception {
		
		List<TransactionDto> dtos = new ArrayList<TransactionDto>();

	    for (Transaction transaction: transactions)
	    	dtos.add(toDto(transaction));
	    
	    return dtos;
		
	}
	
	
	
	
}
