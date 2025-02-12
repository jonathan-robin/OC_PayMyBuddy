package com.oc.paymybuddy.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
 
	@Column(name = "user_from")
	private int userFrom;

	@Column(name = "amount")
	private Double amount;
	
	@Column(name ="date")
	private Date date;
	
	@Column(name="description")
	private String description;
	
    @Column(name="user_to")
    private int userTo;

	public int getId() {
		return id;
	}

	public int getUserFrom() {
		return userFrom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUserFrom(int userFrom) {
		this.userFrom = userFrom;
	}

	public int getUserTo() {
		return userTo;
	}

	public void setUserTo(int userTo) {
		this.userTo = userTo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", userFrom=" + userFrom +  ", amount=" + amount
				+ ", date=" + date + ", description=" + description + ", _userTo=" + userTo +"]";
	}

}
