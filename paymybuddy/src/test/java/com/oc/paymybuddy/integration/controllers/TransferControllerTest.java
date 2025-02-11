package com.oc.paymybuddy.integration.controllers;

import com.oc.paymybuddy.controller.TransferController;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @InjectMocks
    private TransferController transferController;

    @Mock
    private UserConnectionService userConSvc;

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private Model model;

    private UserDetails mockUserDetails;
    private User mockUser;
    private List<UserConnection> mockUserConnections;
    private List<Transaction> mockTransactions;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("1");
        
        mockUser = new User();
 	    mockUser.setId(1);
 	    mockUser.setUsername("1");
 	    mockUser.setEmail("1");
 		
 	    when(userService.findUser(any(UserDetails.class))).thenReturn(mockUser);
 		when(userService.findUserById(any())).thenReturn(Optional.of(mockUser));

        // Mock UserConnection
        UserConnection userConnection = new UserConnection();
        userConnection.setUserConnection(mockUser); // Example connection to the mock user
        mockUserConnections = Arrays.asList(userConnection);

        // Mock Transaction
        Transaction transaction = new Transaction();

        mockTransactions = Arrays.asList(transaction);

        when(userConSvc.getUserConnection(any())).thenReturn(mockUserConnections);
        when(transactionService.findTransactionByUserId(any())).thenReturn(mockTransactions);

        // Simulate Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithMockUser(username = "1", roles = "USER", password = "1")
    public void testTransferPage() throws Exception {

		
	    mockMvc.perform(get("/transfer"))
	            .andExpect(status().isOk())
	            .andExpect(view().name("transfer"))
	            .andExpect(model().attributeExists("users"))
	            .andExpect(model().attributeExists("transactions"))
	            .andExpect(model().attributeExists("connection"));
    }

    @Test
    @WithMockUser(username = "1", roles = "USER", password = "1")
    public void testAddConnectionPage() throws Exception {
        mockMvc.perform(get("/transfer/connections"))
                .andExpect(status().isOk())
                .andExpect(view().name("connections"))
                .andExpect(model().attributeExists("connections"));
    }

}
