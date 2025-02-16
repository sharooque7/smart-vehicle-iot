package com.ainzson.smartvehiclemanagement.services;


import com.ainzson.smartvehiclemanagement.entity.User;
import com.ainzson.smartvehiclemanagement.repository.user.UserRepository;
import com.ainzson.smartvehiclemanagement.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPasswordHash())) {
            return jwtUtil.generateToken(user.get().getUsername());
        }
        throw new IllegalArgumentException("Invalid credentials");
    }
}
