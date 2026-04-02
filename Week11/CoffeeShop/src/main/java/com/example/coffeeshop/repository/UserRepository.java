package com.example.coffeeshop.repository;

import com.example.coffeeshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Returns Optional<User> instead of List<User> for better handling
    Optional<User> findByUserName(String userName);

    // Check if username exists
    boolean existsByUserName(String userName);

    // Check if email exists
    boolean existsByEmail(String email);

    // Alternative method if you want to keep List (but Optional is better)
    // List<User> findByUserName(String userName);
}