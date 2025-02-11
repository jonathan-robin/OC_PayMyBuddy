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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.oc.paymybuddy.controller.UserController;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.repository.TransactionRepository;
import com.oc.paymybuddy.repository.UserRepository;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserService;


public class TransactionServiceTest {
	
	Logger logger = LoggerFactory.getLogger(TransactionServiceTest.class);	

	@InjectMocks
	private TransactionService transactionService; 

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private TransactionRepository transactionRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = Mockito.spy(transactionService);
    }

    @Test
    void testGetTransactions() {
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();

        when(transactionRepo.findAll()).thenReturn(Arrays.asList(transaction1, transaction2));

        Iterable<Transaction> result = transactionService.getTransactions();

        assertNotNull(result);
        assertEquals(2, ((List<Transaction>) result).size());
        verify(transactionRepo, times(1)).findAll();
    }

    @Test
    void testFindTransactionByUserId() {
        Integer userId = 1001;
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();

        when(transactionRepo.findTransactionByUserFrom(userId)).thenReturn(Arrays.asList(transaction1, transaction2));

        List<Transaction> result = transactionService.findTransactionByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepo, times(1)).findTransactionByUserFrom(userId);
    }

    @Test
    void testCreateTransaction_Success() throws Exception {
        User user = new User();
        user.setId(1);
        user.setBalance(100.0);

        User userTo = new User();
        userTo.setId(2);
        userTo.setBalance(50.0);

        Transaction transaction = new Transaction();
        transaction.setUserFrom(user.getId());
        transaction.setUserTo(userTo.getId());
        transaction.setAmount(30.0);

        when(userService.findUserById(transaction.getUserFrom())).thenReturn(Optional.of(user));
        when(userService.findUserById(transaction.getUserTo())).thenReturn(Optional.of(userTo));
        when(transactionRepo.save(any(Transaction.class))).thenReturn(transaction);
        
        Transaction result = transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals(70.0, user.getBalance());
        assertEquals(80.0, userTo.getBalance());
        verify(userService, times(1)).findUserById(user.getId());
        verify(userService, times(1)).findUserById(userTo.getId());
        verify(transactionRepo, times(1)).save(transaction);
        verify(transactionService, times(1)).createTransaction(transaction);

    }

    @Test
    void testCreateTransaction_InsufficientBalance() throws Exception {
    	
    	User user = new User();
        user.setId(1);
        user.setBalance(50.0);

        User userTo = new User();
        userTo.setId(2);
        userTo.setBalance(50.0);
        
        Transaction transaction = new Transaction();
        transaction.setUserFrom(user.getId());
        transaction.setUserTo(userTo.getId());
        transaction.setAmount(70.0);

        when(userService.findUserById(transaction.getUserFrom())).thenReturn(Optional.of(user));
        when(userService.findUserById(transaction.getUserTo())).thenReturn(Optional.of(userTo));
        when(transactionRepo.save(any(Transaction.class))).thenReturn(transaction);
        
        Exception exception = assertThrows(Exception.class, () -> transactionService.createTransaction(transaction));

        assertEquals("Not enough money to make the transaction!" + System.lineSeparator() + "Your balance is 50.0", exception.getMessage());
        verify(userService, times(1)).findUserById(user.getId());
        verify(userService, times(1)).findUserById(userTo.getId());
        
        
        verify(transactionRepo, never()).save(transaction);
    }
}
