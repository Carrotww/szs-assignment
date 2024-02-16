package com.carrotww.taxrefundassignment.user.service;

import com.carrotww.taxrefundassignment.common.configuration.JwtTokenService;
import com.carrotww.taxrefundassignment.user.dto.UserLoginRequest;
import com.carrotww.taxrefundassignment.user.dto.UserRegistrationRequest;
import com.carrotww.taxrefundassignment.user.entity.User;
import com.carrotww.taxrefundassignment.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService jwtTokenProvider;

    public User registerUser(UserRegistrationRequest request) {
        User newUser = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .regNo(passwordEncoder.encode(request.getRegNo()))
                .build();
        return userRepository.save(newUser);
    }

    public String loginUser(UserLoginRequest loginRequest) {
        userRepository.findByUserId(loginRequest.getUserId())
                .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .orElseThrow(() -> new RuntimeException("패스워드가 틀립니다."));

        return jwtTokenProvider.generateToken(loginRequest);
    }
}