package com.carrotww.taxrefundassignment.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@Table(name = "user_tax_refund_calculation")
public class UserTaxRefundCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // 총소득 (총지급액)
    private String totalIncome;
    // 산출세액
    private String calculatedTax;
    // 의료비
    private String medicalExpense;
    // 교육비
    private String educationExpense;
    // 기부금
    private String donationExpense;
    // 보험료
    private String insuranceExpense;
    // 퇴직연금 총납임금액
    private String totalPensionContribution;
}