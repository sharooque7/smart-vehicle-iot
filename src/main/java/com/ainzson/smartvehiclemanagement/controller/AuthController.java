//package com.ainzson.smartvehiclemanagement.controller;
//
//
//import com.ainzson.smartvehiclemanagement.services.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
////@RestController
////@RequestMapping("/api/auth")
////@RequiredArgsConstructor
//public class AuthController {
//
////    private final AuthService authService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
//        try {
//            String token = authService.authenticateUser(request.get("email"), request.get("password"));
//            return ResponseEntity.ok(Map.of("token", token));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//    }
//}
//
