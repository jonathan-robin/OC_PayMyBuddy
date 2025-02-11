package com.oc.paymybuddy.integration.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;

import com.oc.paymybuddy.controller.TransactionController;
import com.oc.paymybuddy.controller.ViewController;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.service.CustomUserDetailsService;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ViewController viewController;

    @Mock
    private Model model;
    
    @Mock
    private UserConnectionService UserConSvc;

    @InjectMocks
    private TransactionController transactionController;
    
    @Mock
    CustomUserDetailsService userDetailsSvc;

    @Mock
    private UserConnectionService userConSvc;

    @Test
    @WithMockUser(username = "1", roles = "USER", password = "1")
    void testAddTransaction_Success() throws Exception {
    	UserDetails userDetails = User.withUsername("testUser").password("password").roles("USER").build();
    	userDetailsSvc.loadUserByUsername("testUser");
        com.oc.paymybuddy.model.User mockUser = new com.oc.paymybuddy.model.User();
        mockUser.setId(1);

        // Mock transaction
        Transaction transaction = new Transaction();
        transaction.setUserFrom(1);
        transaction.setUserTo(2);
        transaction.setAmount(100.0);
        transaction.setDate(new Date(System.currentTimeMillis()));

        when(userService.findUser(userDetails)).thenReturn(mockUser);
        when(viewController.showTransactions(model, userDetails)).thenReturn("transactionView");

        // Perform POST request
        mockMvc.perform(post("/transaction")
                .param("amount", "100.0")
                .param("userTo", "2")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "1", roles = "USER", password = "1")
    void testAddTransaction_InvalidAmount() throws Exception {
        UserDetails userDetails = User.withUsername("testUser").password("password").roles("USER").build();
        com.oc.paymybuddy.model.User mockUser = new com.oc.paymybuddy.model.User();
        mockUser.setId(1);

        when(userService.findUser(userDetails)).thenReturn(mockUser);

        mockMvc.perform(post("/transaction")
                .param("amount", "-50.0")
                .param("userTo", "2")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"));
    }

    @Test
    @WithMockUser(username = "1", roles = "USER", password = "1")
    void testAddTransaction_SendingToSelf() throws Exception {
        UserDetails userDetails = User.withUsername("testUser").password("password").roles("USER").build();
        
        com.oc.paymybuddy.model.User mockUser = new com.oc.paymybuddy.model.User();
        mockUser.setId(1);

        when(userService.findUser(userDetails)).thenReturn(mockUser);

        mockMvc.perform(post("/transaction")
                .param("amount", "100.0")
                .param("userTo", "1")  // Sending to self
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"));
    }
}
