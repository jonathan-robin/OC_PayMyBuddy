package com.oc.paymybuddy.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_connection")
public class UserConnection {

	@Override
	public String toString() {
		return "UserConnection [userConnectionId=" + userConnectionId + ", user=" + user + ", UserConnection="
				+ UserConnection + "]";
	}

	@EmbeddedId
	private UserConnectionId userConnectionId;
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "connection_user_id", referencedColumnName = "id")
    private User UserConnection;
    
    public UserConnection() { 
    	this.userConnectionId = new UserConnectionId();
    }
    
    public User getUser() { 
    	return this.user;
    }
	
	public User getUserConnection() {
		return UserConnection;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUserConnection(User userConnection) {
		UserConnection = userConnection;
	}

}
