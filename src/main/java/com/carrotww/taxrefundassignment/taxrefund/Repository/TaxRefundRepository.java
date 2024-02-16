package com.carrotww.taxrefundassignment.taxrefund.Repository;

import com.carrotww.taxrefundassignment.taxrefund.entity.TaxRefund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRefundRepository extends JpaRepository<TaxRefund, Long> {
}
