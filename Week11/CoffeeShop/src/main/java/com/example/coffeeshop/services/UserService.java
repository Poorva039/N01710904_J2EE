package com.example.coffeeshop.services;

import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    // Add a new user
    public User addUser(User user) {
        logger.info("Adding a new user: {}", user.getUserName());

        // Set created and modified dates
        user.setCreatedDate(LocalDateTime.now());
        user.setModifiedDate(LocalDateTime.now());

        // Set default account status
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    // Get user by username
    public User getUserByUserName(String userName) {
        logger.info("Fetching user by username: {}", userName);
        Optional<User> userOpt = userRepository.findByUserName(userName);
        return userOpt.orElse(null);
    }

    // Check if username is already taken
    public boolean isUserNameTaken(String userName) {
        logger.info("Checking if username exists: {}", userName);
        return userRepository.existsByUserName(userName);
    }

    // Check if email is already registered
    public boolean isEmailTaken(String email) {
        logger.info("Checking if email exists: {}", email);
        return userRepository.existsByEmail(email);
    }

    // User login authentication
    public User userLogin(String userName, String password) {
        logger.info("Authenticating user: {}", userName);

        Optional<User> userOpt = userRepository.findByUserName(userName);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Check password
            if (user.getPassword().equals(password)) {
                // Check if account is active and not locked
                if (user.isEnabled() && user.isAccountNonExpired() &&
                        user.isAccountNonLocked() && user.isCredentialsNonExpired()) {
                    logger.info("User {} authenticated successfully", userName);
                    return user;
                } else {
                    logger.warn("User {} account is disabled or locked", userName);
                    return null;
                }
            } else {
                logger.warn("Invalid password for user: {}", userName);
                return null;
            }
        } else {
            logger.warn("User not found: {}", userName);
            return null;
        }
    }

    // Alternative login method using stream (if you want to keep List approach)
    public User userLoginWithList(String userName, String password) {
        logger.info("Authenticating user with list approach: {}", userName);

        // Note: This would require changing repository method to return List
        // List<User> userList = userRepository.findByUserName(userName);
        // return userList.stream()
        //         .filter(user -> user.getPassword().equals(password))
        //         .findFirst()
        //         .orElse(null);

        // Using Optional approach (recommended)
        return userLogin(userName, password);
    }

    // Update user information
    public User updateUser(User user) {
        logger.info("Updating user: {}", user.getUserName());
        user.setModifiedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    // Get user by ID
    public User getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElse(null);
    }
}