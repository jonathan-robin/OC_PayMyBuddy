package com.oc.paymybuddy.integration.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.model.UserConnection;
import com.oc.paymybuddy.repository.UserRepository;
import com.oc.paymybuddy.service.CustomUserDetailsService;

public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService userDetailsService;
    
    @Mock
    private UserRepository userRepo;
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
    	
    	User user = new User();
    	
    	String email = "test";
        String password = "password";
        String role = "ROLE_USER";
        
    	user.setEmail(email);
    	user.setId(1);
    	user.setPassword(password);
    	user.setRole(role);
    	
               
    	when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername(email);

        assertNotNull(result);
        assertEquals(email, result.getUsername());
        verify(userRepo, times(1)).findByEmail(any(String.class));
    }
    
    @Test
    void testLoadUserByUsername_Fail() {
    	
    	User user = new User();
    	String email = "test"; 
    	user.setEmail(email);
    	user.setId(1);
               
    	when(userRepo.findByEmail(email)).thenReturn(null);

//        UserDetails result = userDetailsService.loadUserByUsername(email);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));

        assertEquals(UsernameNotFoundException.class, exception.getClass());

    	
    }

}
