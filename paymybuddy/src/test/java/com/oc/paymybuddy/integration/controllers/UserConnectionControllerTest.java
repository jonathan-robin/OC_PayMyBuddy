package com.oc.paymybuddy.integration.controllers;

import com.oc.paymybuddy.controller.UserConnectionController;
import com.oc.paymybuddy.controller.ViewController;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.repository.UserConnectionRepository;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;


import static org.mockito.Mockito.*;

@SpringBootTest
class UserConnectionControllerTest {

    @MockBean
    private UserService userSvc;

    @MockBean
    private UserConnectionService userConSvc;
    
    @Mock
    private ViewController viewController;

    @Mock
    private UserDetails userDetails;

    @MockBean
    private UserConnectionController userConnectionController;
    
    @Mock
    private UserConnectionRepository userConRepo;

    private User userFrom;
    private User userTo;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        userFrom = new User();
        userFrom.setEmail("from@example.com");
        userFrom.setFirstname("John");
        userFrom.setId(1000);

        userTo = new User();
        userTo.setEmail("to@example.com");
        userTo.setFirstname("Jane");
        userTo.setId(2000);
        
        when(userSvc.findUser(userDetails)).thenReturn(userFrom);
    }
    
    @Test
    @WithMockUser(username = "1", roles = "USER", password = "1")
    void testAddUserConnection_success() throws Exception {
        when(userConSvc.checkIfUserConnectionIsAlreadyExisting(userFrom, userTo)).thenReturn(false);
        when(userConSvc.checkIfUserConnectionTryToAddHimself(userFrom, userTo)).thenReturn(false);
        userConnectionController.addUserConnection(mock(Model.class), userFrom.getEmail(), userDetails);
        userConRepo.findUserConnectionByUserFromIdAndUserToId(userFrom.getId(), userTo.getId());
    }
    
}