package com.oc.paymybuddy.dto;

import com.oc.paymybuddy.model.User;

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
