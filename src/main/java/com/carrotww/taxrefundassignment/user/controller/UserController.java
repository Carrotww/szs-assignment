package com.carrotww.taxrefundassignment.user.controller;

import com.carrotww.taxrefundassignment.user.dto.JwtAuthenticationResponse;
import com.carrotww.taxrefundassignment.user.dto.UserLoginRequest;
import com.carrotww.taxrefundassignment.user.dto.UserRegistrationRequest;
import com.carrotww.taxrefundassignment.user.entity.User;
import com.carrotww.taxrefundassignment.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("szs")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        User newUser = userService.registerUser(request);
        return ResponseEntity.ok("Success");
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        String token = userService.loginUser(loginRequest);
        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();

        return ResponseEntity.ok(response);
    }
}
