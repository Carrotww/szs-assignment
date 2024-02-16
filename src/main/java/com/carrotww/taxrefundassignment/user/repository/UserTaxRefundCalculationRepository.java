package com.carrotww.taxrefundassignment.user.repository;

import com.carrotww.taxrefundassignment.user.entity.User;
import com.carrotww.taxrefundassignment.user.entity.UserTaxRefundCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTaxRefundCalculationRepository extends JpaRepository<UserTaxRefundCalculation, Long> {
    Optional<UserTaxRefundCalculation> findByUser(User user);
}
