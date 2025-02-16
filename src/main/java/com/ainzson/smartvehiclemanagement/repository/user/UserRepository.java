package com.ainzson.smartvehiclemanagement.repository.user;

import com.ainzson.smartvehiclemanagement.entity.User;

import java.util.Optional;

public interface UserRepository {

        User save(User user); // Save or update user

        Optional<User> findById(Long userId); // Find user by ID

        Optional<User> findByEmail(String email); // Find user by email

        boolean existsByUsername(String username); // Check if username exists

        boolean existsByEmail(String email); // Check if email exists
    }
