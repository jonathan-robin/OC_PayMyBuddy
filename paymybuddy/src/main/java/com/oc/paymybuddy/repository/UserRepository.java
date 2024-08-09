package com.oc.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oc.paymybuddy.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	
}
