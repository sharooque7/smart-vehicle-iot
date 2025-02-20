package com.ainzson.smartvehiclemanagement.services;

import com.ainzson.smartvehiclemanagement.entity.User;
import com.ainzson.smartvehiclemanagement.repository.user.UserRepositoryService;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryService userRepositoryService;
    private final PasswordEncoder passwordEncoder; // For hashing passwords

    //     User Registration
    public User registerUser(User user) {
        // Check if username or email already exists
        if (userRepositoryService.isUsernameTaken(user.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepositoryService.isEmailRegistered(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Hash password before saving
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        return userRepositoryService.saveUser(user);
    }

    public User updateUser(Long userId, User updatedUser) {
        return userRepositoryService.getUserById(userId).map(user -> {
            user.setFullName(updatedUser.getFullName());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setEmail(updatedUser.getEmail());
            if (updatedUser.getPasswordHash() != null) {
                user.setPasswordHash(passwordEncoder.encode(updatedUser.getPasswordHash()));
            }
            return userRepositoryService.saveUser(user);
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public void deleteUser(Long userId) {
        userRepositoryService.deleteById(userId);
    }

    // Get User by ID
    public Optional<User> getUserById(Long userId) {
        return userRepositoryService.getUserById(userId);
    }

    // Get User by Email
    public Optional<User> getUserByEmail(String email) {
        return userRepositoryService.getUserByEmail(email);
    }

}
