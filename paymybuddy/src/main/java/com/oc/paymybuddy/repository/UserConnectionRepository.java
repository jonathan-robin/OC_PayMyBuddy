package com.oc.paymybuddy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oc.paymybuddy.model.UserConnection;

@Repository
public interface UserConnectionRepository extends CrudRepository<UserConnection, Integer>{

	List<UserConnection> findUserConnectionByUserId(@Param("userId") Integer userId);	
}
