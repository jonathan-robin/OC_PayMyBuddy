package com.oc.paymybuddy.integration.controllers;

import com.oc.paymybuddy.controller.ViewController;
import com.oc.paymybuddy.dto.TransactionDto;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ViewControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserConnectionService userConnectionService;

    @Mock
    private Model model;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private ViewController viewController;

    private User user;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setId(1);

        when(userService.findUser(userDetails)).thenReturn(user);
    }

    @Test
    void testHomePage() {
        String result = viewController.homePage(model);
        assertEquals("home", result);
    }

    @Test
    void testContactPage() {
        String result = viewController.contact(model);
        assertEquals("contact", result);
    }

    @Test
    void testLoginView() {
        String result = viewController.loginView(model);
        assertEquals("login", result);
    }

    @Test
    void testProfilePage() throws Exception {
        when(userService.findUser(userDetails)).thenReturn(user);
        String result = viewController.profile(model, userDetails);
        verify(userService).findUser(userDetails);
        assertEquals("profile", result);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testShowTransactions() throws Exception {
        List<User> connectedUsers = Arrays.asList(user);
        List<TransactionDto> transactionsDto = Arrays.asList(new TransactionDto());

        when(userService.getAllConnectedUser(user)).thenReturn(connectedUsers);
        when(transactionService.findTransactionByUserId(user.getId())).thenReturn(Arrays.asList(new Transaction()));
        when(transactionService.toDto(anyList())).thenReturn(transactionsDto);

        String result = viewController.showTransactions(model, userDetails);

        assertEquals("transfer", result);
        verify(model).addAttribute("users", connectedUsers);
        verify(model).addAttribute("transactions", transactionsDto);
    }

    @Test
    void testShowConnections() throws Exception {
    	UserConnection userCon = new UserConnection();
        List<UserConnection> connections = Arrays.asList(userCon);

        when(userConnectionService.getUserConnection(user)).thenReturn(connections);

        String result = viewController.showConnections(model, userDetails);

        assertEquals("connections", result);
        verify(model).addAttribute("connections", connections);
    }
}
