package com.carrotww.taxrefundassignment.common.service;

import com.carrotww.taxrefundassignment.user.entity.User;
import com.carrotww.taxrefundassignment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저아이디가 존재하지 않습니다 : " + userId));

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), new ArrayList<>());
    }
}
