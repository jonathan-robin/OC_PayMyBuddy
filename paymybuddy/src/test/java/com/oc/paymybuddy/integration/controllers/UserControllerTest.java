package com.oc.paymybuddy.integration.controllers;

import com.oc.paymybuddy.controller.UserController;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private UserController userController;

    private User validUser;
    private User invalidUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        validUser = new User();
        validUser.setFirstname("John");
        validUser.setLastname("Doe");
        validUser.setEmail("john.doe@example.com");
        validUser.setPassword("password123");
        validUser.setUsername("john.doe");
        
        invalidUser = new User();
        invalidUser.setFirstname("John");
        invalidUser.setLastname("Doe");
        invalidUser.setEmail("john.doe@example.com");
    }

    @Test
    void testAddUser_success() throws Exception {
        when(userService.createUser(validUser)).thenReturn(validUser);

        String result = userController.addUser(validUser, model, request, response);

        assertEquals("login", result);
        verify(userService).createUser(validUser);
    }

    @Test
    void testAddUser_missingInformation() throws Exception {
        when(userService.createUser(invalidUser)).thenReturn(null);

        String result = userController.addUser(invalidUser, model, request, response);

        assertEquals("sign-in/signIn-error", result);
        verify(userService, times(0)).createUser(invalidUser);
    }

    @Test
    void testAddUser_errorCreatingUser() throws Exception {
        when(userService.createUser(validUser)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            userController.addUser(validUser, model, request, response);
        });

        assertEquals("Error when creating new user", exception.getMessage());
    }
}
