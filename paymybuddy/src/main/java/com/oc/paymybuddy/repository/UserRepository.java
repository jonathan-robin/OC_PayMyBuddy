package com.oc.paymybuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oc.paymybuddy.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
		
	public Optional<User> findByEmail(String email);
	
	public List<User> findAll();
	
}
