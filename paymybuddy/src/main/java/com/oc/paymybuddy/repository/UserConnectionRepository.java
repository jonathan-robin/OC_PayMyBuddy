package com.oc.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oc.paymybuddy.model.UserConnection;

@Repository
public interface UserConnectionRepository extends CrudRepository<UserConnection, Integer>{

}
