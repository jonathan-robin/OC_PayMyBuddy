package com.oc.paymybuddy.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserConnectionId implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "connection_user_id")
    private int connectionUserId;    

    public UserConnectionId() {}

    public UserConnectionId(int userId, int connectionUserId) {
        this.userId = userId;
        this.connectionUserId = connectionUserId;
    }

    // Getters et Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getConnectionUserId() {
        return connectionUserId;
    }

    public void setConnectionUserId(int connectionUserId) {
        this.connectionUserId = connectionUserId;
    }

    // Méthodes equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserConnectionId that = (UserConnectionId) o;
        return userId == that.userId && connectionUserId == that.connectionUserId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, connectionUserId);
    }
}
