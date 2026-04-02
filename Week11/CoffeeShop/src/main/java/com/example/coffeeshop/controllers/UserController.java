package com.example.coffeeshop.controllers;

import com.example.coffeeshop.models.User;
import com.example.coffeeshop.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Landing page - Login form
    @GetMapping("/")
    public String getHomePage(Model model) {
        logger.info("Accessing login page");
        model.addAttribute("user", new User());
        return "index";
    }

    // Show signup/registration form
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        logger.info("Showing signup form");
        model.addAttribute("user", new User());
        return "signup";
    }

    // Process new user registration with field-level validation
    @PostMapping("/signup")
    public String addNewUser(@ModelAttribute User user, Model model) {
        logger.info("Received signup request for user: {}", user.getUserName());

        boolean hasErrors = false;

        // Validate First Name
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            model.addAttribute("firstNameError", "First name is required!");
            hasErrors = true;
        } else if (user.getFirstName().length() < 2) {
            model.addAttribute("firstNameError", "First name must be at least 2 characters!");
            hasErrors = true;
        }

        // Validate Last Name
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            model.addAttribute("lastNameError", "Last name is required!");
            hasErrors = true;
        } else if (user.getLastName().length() < 2) {
            model.addAttribute("lastNameError", "Last name must be at least 2 characters!");
            hasErrors = true;
        }

        // Validate Email
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            model.addAttribute("emailError", "Email is required!");
            hasErrors = true;
        } else if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            model.addAttribute("emailError", "Please enter a valid email address (e.g., name@example.com)!");
            hasErrors = true;
        } else if (userService.isEmailTaken(user.getEmail())) {
            model.addAttribute("emailError", "Email already registered! Please use a different email.");
            hasErrors = true;
        }

        // Validate Username
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            model.addAttribute("userNameError", "Username is required!");
            hasErrors = true;
        } else if (user.getUserName().length() < 3) {
            model.addAttribute("userNameError", "Username must be at least 3 characters!");
            hasErrors = true;
        } else if (userService.isUserNameTaken(user.getUserName())) {
            model.addAttribute("userNameError", "Username already taken! Please choose a different username.");
            hasErrors = true;
        }

        // Validate Password
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            model.addAttribute("passwordError", "Password is required!");
            hasErrors = true;
        } else if (user.getPassword().length() < 6) {
            model.addAttribute("passwordError", "Password must be at least 6 characters!");
            hasErrors = true;
        } else if (!user.getPassword().matches(".*[A-Z].*")) {
            model.addAttribute("passwordError", "Password must contain at least one uppercase letter!");
            hasErrors = true;
        } else if (!user.getPassword().matches(".*[0-9].*")) {
            model.addAttribute("passwordError", "Password must contain at least one number!");
            hasErrors = true;
        }

        // Validate Date of Birth
        if (user.getDob() == null || user.getDob().trim().isEmpty()) {
            model.addAttribute("dobError", "Date of Birth is required!");
            hasErrors = true;
        }

        // Validate Gender (optional but recommended)
        if (user.getGender() == null || user.getGender().trim().isEmpty()) {
            model.addAttribute("genderError", "Please select your gender!");
            hasErrors = true;
        }

        // If any validation errors, return to signup page with errors
        if (hasErrors) {
            model.addAttribute("user", user);
            logger.warn("Validation failed for user registration: {}", user.getUserName());
            return "signup";
        }

        try {
            // Add the new user
            userService.addUser(user);
            logger.info("User {} registered successfully", user.getUserName());
            model.addAttribute("globalSuccess", "Registration successful! Please login with your credentials.");
            return "index";

        } catch (Exception e) {
            logger.error("Error during user registration: {}", e.getMessage());
            model.addAttribute("globalError", "Registration failed! Please try again.");
            model.addAttribute("user", user);
            return "signup";
        }
    }

    // Process user login
    @PostMapping("/login")
    public String userLogin(@RequestParam String userName,
                            @RequestParam String password,
                            Model model) {
        logger.info("Received login request for user: {}", userName);

        try {
            // Validate input
            if (userName == null || userName.trim().isEmpty()) {
                model.addAttribute("error", "Username is required!");
                model.addAttribute("user", new User());
                return "index";
            }

            if (password == null || password.trim().isEmpty()) {
                model.addAttribute("error", "Password is required!");
                model.addAttribute("user", new User());
                return "index";
            }

            User user = userService.userLogin(userName, password);

            if (user != null) {
                logger.info("User {} logged in successfully", userName);
                model.addAttribute("user", user);
                model.addAttribute("message", "Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
                return "home";
            } else {
                logger.warn("Authentication failed for user: {}", userName);
                model.addAttribute("error", "Invalid Username/Password");
                model.addAttribute("user", new User());
                return "index";
            }

        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage());
            model.addAttribute("error", "Login failed! Please try again.");
            model.addAttribute("user", new User());
            return "index";
        }
    }

    // Display home page with coffee shop information
    @GetMapping("/home")
    public String getHomePageAfterLogin(@RequestParam(required = false) String username, Model model) {
        logger.info("Accessing home page");

        // If username is provided, fetch user details
        if (username != null && !username.isEmpty()) {
            User user = userService.getUserByUserName(username);
            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("message", "Welcome " + user.getFirstName() + " " + user.getLastName() +
                        "! This is the debut homepage for our Java Beans & Bytes coffee shop, " +
                        "crafted to share our passion for coffee and create a welcoming experience for all.");
            } else {
                model.addAttribute("message", "This is the debut homepage for our Java Beans & Bytes coffee shop, " +
                        "crafted to share our passion for coffee and create a welcoming experience for all.");
            }
        } else {
            model.addAttribute("message", "This is the debut homepage for our Java Beans & Bytes coffee shop, " +
                    "crafted to share our passion for coffee and create a welcoming experience for all.");
        }

        return "home";
    }

    // Logout functionality
    @GetMapping("/logout")
    public String logout(Model model) {
        logger.info("User logged out");
        model.addAttribute("success", "You have been successfully logged out!");
        model.addAttribute("user", new User());
        return "index";
    }

    // Display user profile
    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable String username, Model model) {
        logger.info("Viewing profile for user: {}", username);
        User user = userService.getUserByUserName(username);

        if (user != null) {
            model.addAttribute("user", user);
            return "profile";
        } else {
            model.addAttribute("error", "User not found!");
            return "index";
        }
    }
}