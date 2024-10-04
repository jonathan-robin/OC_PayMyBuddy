package com.oc.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oc.paymybuddy.model.User;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping("")
    public String loginView(Model model) {
    	model.addAttribute("login", new User());
        return "login";
    }

    @PostMapping("")
    public String login(@ModelAttribute("login") User login) {
        return "home";
    }
    
    @GetMapping("/error/")
    public String loginError(@ModelAttribute("login") User login) {
        return "login-error";
    }
	
}
