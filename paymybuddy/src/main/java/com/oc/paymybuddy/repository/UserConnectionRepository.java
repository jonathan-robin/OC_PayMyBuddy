package com.oc.paymybuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.model.UserConnectionId;

@Repository
public interface UserConnectionRepository extends CrudRepository<UserConnection, UserConnectionId>{

	List<UserConnection> findUserConnectionByUserId(@Param("userId") Integer userId);	

	@Query("SELECT uc FROM UserConnection uc WHERE uc.user.id = :userFromId AND uc.userConnection.id = :userToId")
	Optional<UserConnection> findUserConnectionByUserFromIdAndUserToId(@Param("userFromId") Integer userFromId, @Param("userToId") Integer userToId);

	
}
