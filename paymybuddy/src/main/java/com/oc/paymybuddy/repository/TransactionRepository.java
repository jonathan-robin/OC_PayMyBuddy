package com.oc.paymybuddy.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oc.paymybuddy.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

	List<Transaction> findTransactionByUserFrom(Integer userId);
	
}
