package com.oc.paymybuddy.integration.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import com.oc.paymybuddy.dto.TransactionDto;
import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserService;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.repository.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    private User userFrom;
    private User userTo;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userFrom = new User();
        userFrom.setId(1);
        userFrom.setUsername("UserFrom");
        userFrom.setBalance(1000.0);

        userTo = new User();
        userTo.setId(2);
        userTo.setUsername("UserTo");
        userTo.setBalance(500.0);

        transaction = new Transaction();
        transaction.setUserFrom(1);
        transaction.setUserTo(2);
        transaction.setAmount(200.0);
        transaction.setDescription("Test transaction");
        transaction.setDate(new java.sql.Date(System.currentTimeMillis()));
    }

    @Test
    void testGetTransactions() {
        // Mock the repository to return a list of transactions
        List<Transaction> transactions = Arrays.asList(transaction);
        when(transactionRepository.findAll()).thenReturn(transactions);

        Iterable<Transaction> result = transactionService.getTransactions();
        assertNotNull(result);
        assertEquals(1, ((Collection<Transaction>) result).size());
    }

    @Test
    void testFindTransactionByUserId() {
        // Mock the repository to return a list of transactions for a specific user
        List<Transaction> transactions = Arrays.asList(transaction);
        when(transactionRepository.findTransactionByUserFrom(1)).thenReturn(transactions);

        List<Transaction> result = transactionService.findTransactionByUserId(1);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getUserFrom());
    }

    @Test
    void testCreateTransactionSuccess() throws Exception {
        // Mock the userService to return the users
        when(userService.findUserById(1)).thenReturn(Optional.of(userFrom));
        when(userService.findUserById(2)).thenReturn(Optional.of(userTo));

        // Mock the transaction repository to save the transaction
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Call the method
        Transaction result = transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals(1, result.getUserFrom());
        assertEquals(2, result.getUserTo());
        assertEquals(200.0, result.getAmount());
        assertEquals("Test transaction", result.getDescription());

        // Verify the balance changes
        assertEquals(800.0, userFrom.getBalance());
        assertEquals(700.0, userTo.getBalance());
    }

    @Test
    void testCreateTransactionInsufficientBalance() throws Exception {
        // Change balance to an insufficient amount for userFrom
        userFrom.setBalance(50.0);

        // Mock the userService to return the users
        when(userService.findUserById(1)).thenReturn(Optional.of(userFrom));
        when(userService.findUserById(2)).thenReturn(Optional.of(userTo));

        // Expect an exception due to insufficient balance
        assertThrows(Exception.class, () -> {
            transactionService.createTransaction(transaction);
        });
    }

    @Test
    void testToDto() throws Exception {
        // Mock the userService to return the users
        when(userService.findUserById(1)).thenReturn(Optional.of(userFrom));
        when(userService.findUserById(2)).thenReturn(Optional.of(userTo));

        // Call the method
        TransactionDto result = transactionService.toDto(transaction);

        assertNotNull(result);
        assertEquals(1, result.getUserFrom().getId());
        assertEquals(2, result.getUserTo().getId());
        assertEquals(200.0, result.getAmount());
        assertEquals("Test transaction", result.getDescription());
    }

    @Test
    void testToDtoList() throws Exception {
        // Mock the userService to return the users
        when(userService.findUserById(1)).thenReturn(Optional.of(userFrom));
        when(userService.findUserById(2)).thenReturn(Optional.of(userTo));

        List<Transaction> transactions = Arrays.asList(transaction);

        // Call the method
        List<TransactionDto> result = transactionService.toDto(transactions);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getUserFrom().getId());
        assertEquals(2, result.get(0).getUserTo().getId());
    }
}
