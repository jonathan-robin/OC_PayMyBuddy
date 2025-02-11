package com.oc.paymybuddy.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.paymybuddy.model.Transaction;
import com.oc.paymybuddy.model.User;
import com.oc.paymybuddy.service.TransactionService;
import com.oc.paymybuddy.service.UserService;

/**
 * Controller responsible for handling user transactions.
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private UserService userSvc;

    @Autowired
    private TransactionService transactionSvc;

    @Autowired
    private ViewController viewController;

    /**
     * Retrieves the authenticated user's details.
     *
     * @param userDetails the authenticated user's details
     * @return a string containing the user's username
     */
    public String getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "User Details: " + userDetails.getUsername();
    }

    /**
     * Handles the creation of a new transaction.
     *
     * @param model         the model to store attributes
     * @param transaction   the transaction details
     * @param userDetails   the authenticated user's details
     * @return the view name after processing the transaction
     * @throws Exception if an error occurs during transaction creation
     */
    @PostMapping("")
    public String addTransaction(Model model, @ModelAttribute("connection") Transaction transaction, @AuthenticationPrincipal UserDetails userDetails) throws Exception {

        User user = userSvc.findUser(userDetails);

        transaction.setUserFrom(user.getId());
        transaction.setDate(new Date(System.currentTimeMillis()));

        // Validate transaction amount
        if (transaction.getAmount() <= 0) {
            model.addAttribute("error", "Amount can't be lower than 0.");
            return viewController.showTransactions(model, userDetails);
        }

        // Ensure a user cannot send money to themselves
        if (transaction.getUserFrom() == transaction.getUserTo()) {
            model.addAttribute("error", "You're not supposed to have a connection with yourself.");
            return viewController.showTransactions(model, userDetails);
        }

        try {
            transactionSvc.createTransaction(transaction);
            return viewController.showTransactions(model, userDetails);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return viewController.showTransactions(model, userDetails);
        }
    }
}
