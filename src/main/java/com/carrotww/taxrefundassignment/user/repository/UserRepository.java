package com.carrotww.taxrefundassignment.user.repository;

import com.carrotww.taxrefundassignment.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
