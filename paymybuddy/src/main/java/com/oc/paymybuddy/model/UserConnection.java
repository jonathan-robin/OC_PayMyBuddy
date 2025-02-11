package com.oc.paymybuddy.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_connection")
public class UserConnection {

    @EmbeddedId
    private UserConnectionId userConnectionId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("connectionUserId")
    @JoinColumn(name = "connection_user_id")
    private User userConnection;

    public UserConnection() {}

    public UserConnection(User user, User userConnection) {
        this.user = user;
        this.userConnection = userConnection;
        this.userConnectionId = new UserConnectionId(user.getId(), userConnection.getId());
    }
    
    // Getters et Setters
    public User getUser() { 
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserConnection() {
        return userConnection;
    }

    public void setUserConnection(User userConnection) {
        this.userConnection = userConnection;
    }

    @Override
    public String toString() {
        return "UserConnection [userConnectionId=" + userConnectionId + ", user=" + user + ", userConnection=" + userConnection + "]";
    }
}
