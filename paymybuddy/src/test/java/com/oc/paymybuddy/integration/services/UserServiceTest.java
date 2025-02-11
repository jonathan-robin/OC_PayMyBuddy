package com.oc.paymybuddy.integration.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oc.paymybuddy.PaymybuddyApplication;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.repository.UserRepository;
import com.oc.paymybuddy.service.CustomUserDetailsService;
import com.oc.paymybuddy.service.UserService;


class UserServiceTest {
	
	Logger logger = LoggerFactory.getLogger(PaymybuddyApplication.class);

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private CustomUserDetailsService customUserSvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepo, times(1)).findByEmail(email);
    }

    @Test
    void testCreateUser_Success() throws Exception {
        User user = new User();
        user.setEmail("859");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password");
        user.setUsername("johndoe");

        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.createUser(user);

        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstname(), result.getFirstname());
        assertNotNull(result.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches("password", result.getPassword()));
        verify(userRepo, times(1)).findByEmail(user.getEmail());
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_EmailAlreadyUsed() {
        User user = new User();
        user.setEmail("test@test52.com");

        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(Exception.class, () -> userService.createUser(user));

        assertEquals("Email already used!", exception.getMessage());
        verify(userRepo, times(1)).findByEmail(user.getEmail());
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void testFindUser_Success() throws Exception {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.findUser(userDetails);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepo, times(1)).findByEmail(email);
    }

    @Test
    void testFindUser_UserNotFound() {
        String email = "test@example.com";

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> userService.findUser(userDetails));

        assertEquals("User can't be found", exception.getMessage());
        verify(userRepo, times(1)).findByEmail(email);
    }

    @Test
    void testFindUserById_Success() throws Exception {
        Integer id = 1;
        User user = mock(User.class); 

        when(user.getId()).thenReturn(id);
        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(id);

        assertNotNull(result);
        assertEquals(id, result.get().getId());
        verify(userRepo, times(1)).findById(id);
    }

    @Test
    void testFindUserById_UserNotFound() {
        Integer id = 1;

        when(userRepo.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> userService.findUserById(id));

        assertEquals("User can't be found", exception.getMessage());
        verify(userRepo, times(1)).findById(id);
    }

    @Test
    void testFindAll() {
        User user1 = new User();
        User user2 = new User();

        when(userRepo.findAll()).thenReturn(Arrays.asList(user1, user2));

        var result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void testFindByEmail_Success() throws Exception {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.findByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepo, times(1)).findByEmail(email);
    }

    @Test
    void testFindByEmail_UserNotFound() {
        String email = "test@example.com";

        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> userService.findByEmail(email));
        assertEquals("User can't be found", exception.getMessage());
        verify(userRepo, times(1)).findByEmail(email);
    }
}
