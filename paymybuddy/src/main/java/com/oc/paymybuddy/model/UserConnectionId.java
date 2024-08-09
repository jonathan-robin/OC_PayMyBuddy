package com.oc.paymybuddy.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;

public class UserConnectionId implements Serializable {

	@Column(name = "user_id", insertable=false, updatable=false)
	private Integer userId;

	@Column(name = "connection_user_id", insertable=false, updatable=false)
	private int connectionUserId;
	

	
}
