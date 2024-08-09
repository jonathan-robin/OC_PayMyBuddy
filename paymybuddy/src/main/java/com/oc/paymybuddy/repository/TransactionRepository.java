package com.oc.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.oc.paymybuddy.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

}
