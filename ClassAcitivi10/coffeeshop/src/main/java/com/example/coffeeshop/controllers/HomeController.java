package com.example.coffeeshop.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHome(Authentication authentication, Model model) {
        model.addAttribute("welcomeName", authentication.getName());
        return "home";
    }
}