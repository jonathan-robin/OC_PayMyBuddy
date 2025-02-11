package com.oc.paymybuddy.dto;

import java.sql.Date;

import com.oc.paymybuddy.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {

	private int id; 
	private User userFrom; 
	private User userTo;
	private Double amount; 
	private String description; 
	private String date; 
	
}
