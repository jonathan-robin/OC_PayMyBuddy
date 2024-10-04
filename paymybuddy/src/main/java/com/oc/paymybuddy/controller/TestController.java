package com.oc.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oc.paymybuddy.model.User;

@Controller
public class TestController {
	
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
    	model.addAttribute("appName", appName);
        return "home";
    }
    
//    @GetMapping("/sign-in")
//    public String signIn(Model model) { 
//    	model.addAttribute("signIn", new User());
//    	return "signIn";
//    }
    
    @GetMapping("/admin")
    public String admin(Model model) {
    	model.addAttribute("AdminPage", appName);
        return "admin";
    }
    
    @GetMapping("/user")
    public String user(Model model) {
    	model.addAttribute("userPage", appName);
        return "user";
    }
	
}
