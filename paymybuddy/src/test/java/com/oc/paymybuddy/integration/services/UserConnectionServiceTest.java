package com.oc.paymybuddy.integration.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.repository.UserConnectionRepository;
import com.oc.paymybuddy.repository.UserRepository;
import com.oc.paymybuddy.service.UserConnectionService;
import com.oc.paymybuddy.service.UserService;

class UserConnectionServiceTest {

    @InjectMocks
    private UserConnectionService userConnectionService;
    
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private UserConnectionRepository userConRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserConnection_Success() {
    	Integer id = 1;
        User user = mock(User.class); 

        when(user.getId()).thenReturn(id);
        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        UserConnection connection1 = new UserConnection(user, new User());
        UserConnection connection2 = new UserConnection(user, new User());

        when(userConRepo.findUserConnectionByUserId(user.getId())).thenReturn(Arrays.asList(connection1, connection2));

        List<UserConnection> result = userConnectionService.getUserConnection(user);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userConRepo, times(1)).findUserConnectionByUserId(user.getId());
    }

    @Test
    void testGetUserConnection_Empty() {
    	Integer id = 1;
        User user = mock(User.class); 

        when(user.getId()).thenReturn(id);
        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        when(userConRepo.findUserConnectionByUserId(user.getId())).thenReturn(Arrays.asList());

        List<UserConnection> result = userConnectionService.getUserConnection(user);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userConRepo, times(1)).findUserConnectionByUserId(user.getId());
    }

    @Test
    void testAddUserConnection_Success() {
    	Integer id = 1;
        User user = mock(User.class); 

        when(user.getId()).thenReturn(id);
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        
    	Integer _id = 1;
        User userTo = mock(User.class); 

        when(userTo.getId()).thenReturn(_id);
        when(userRepo.findById(_id)).thenReturn(Optional.of(userTo));

        UserConnection userConnection = new UserConnection(user, userTo);

        when(userConRepo.save(any(UserConnection.class))).thenReturn(userConnection);

        UserConnection result = userConnectionService.addUserConnection(user, userTo);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(userTo, result.getUserConnection());
        verify(userConRepo, times(1)).save(any(UserConnection.class));
    }

}
