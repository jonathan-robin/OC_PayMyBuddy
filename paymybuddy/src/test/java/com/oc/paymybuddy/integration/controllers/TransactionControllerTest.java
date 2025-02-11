package com.oc.paymybuddy.integration.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;


import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.oc.paymybuddy.PaymybuddyApplication;
import com.oc.paymybuddy.controller.TransactionController;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserService;

@SpringBootTest(classes = PaymybuddyApplication.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userSvc;

    @Mock
    private TransactionService transactionSvc;
    
    @Mock
    private UserDetails mockUserDetails;

    @InjectMocks
    private TransactionController transactionController;
    
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        
        mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("1");
        
        // Simulate Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithMockUser(username = "1", roles = "ADMIN", password = "1")
    public void testAddTransaction_Success() throws Exception {
        // Mocking the UserService
        User mockUserTo = new User();
        mockUserTo.setId(2);
        mockUserTo.setBalance(150D);
        
        User mockUserFrom = new User();
        mockUserFrom.setId(5);;
        mockUserFrom.setBalance(150D);

        // Mocking the TransactionService
        Transaction mockTransaction = new Transaction();
        mockTransaction.setAmount(100D);
        mockTransaction.setUserTo(2);
        mockTransaction.setUserFrom(5);
        mockTransaction.setDate(new Date(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).getDate()));

        
        // Perform the POST request and check if it redirects or returns the expected view
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
        		.param("amount", "100")
        		.param("userTo", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("transfer"))
                .andReturn();
        
        result.getResponse();
    }
    
    @Test
    @WithMockUser(username = "1", roles = "ADMIN", password = "1")
    void testAddTransaction_Failure() throws Exception {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john.doe@example.com");

        User user = new User();
        user.setId(1);
        when(userSvc.findUser(userDetails)).thenReturn(user);

        Transaction transaction = new Transaction();
        transaction.setAmount(-10.0); // Transaction invalide

        mockMvc.perform(post("/transaction")
                .flashAttr("connection", transaction)
                .principal(() -> "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"));
    }
    
    
}
