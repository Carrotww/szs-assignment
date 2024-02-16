package com.carrotww.taxrefundassignment.taxrefund.service;

import com.carrotww.taxrefundassignment.taxrefund.Repository.TaxRefundRepository;
import com.carrotww.taxrefundassignment.taxrefund.dto.RefundResponse;
import com.carrotww.taxrefundassignment.taxrefund.entity.TaxRefund;
import com.carrotww.taxrefundassignment.user.entity.User;
import com.carrotww.taxrefundassignment.user.entity.UserTaxRefundCalculation;
import com.carrotww.taxrefundassignment.user.repository.UserRepository;
import com.carrotww.taxrefundassignment.user.repository.UserTaxRefundCalculationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class TaxRefundService {

    private final UserRepository userRepository;
    private final UserTaxRefundCalculationRepository userTaxRefundCalculationRepository;
    private final TaxRefundRepository taxRefundRepository;

    // 사용자의 세금 환급액을 계산하고 반환합니다.
    public RefundResponse calculateRefund(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저아이디가 존재하지 않습니다 :" + userId));

        UserTaxRefundCalculation calculation = userTaxRefundCalculationRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException(
                        "저장된 유저의 정보가 없습니다 세액계산 API를 먼저 진행해주세요 " + userId));

        // 산출세액
        BigDecimal calculatedTax = new BigDecimal(calculation.getCalculatedTax().replaceAll(",", ""));
        // 총지급액
        BigDecimal totalIncome = new BigDecimal(calculation.getTotalIncome().replaceAll(",", ""));
        // 근로소득세액공제금액
        BigDecimal incomeTaxDeduction = calculateIncomeTaxDeduction(calculatedTax);
        // 특별세액공제금액
        BigDecimal specialDeductionAmount = calculateSpecialDeductionAmount(calculation, totalIncome);
        // 표준세액공제금액
        BigDecimal standardDeductionAmount = calculateStandardDeductionAmount(specialDeductionAmount);
        // 퇴직연금세액공제금액
        BigDecimal pensionDeductionAmount = calculatePensionDeductionAmount(calculation);
        // 결정세액
        BigDecimal finalTaxAmount = calculateFinalTaxAmount(calculatedTax, incomeTaxDeduction,
                specialDeductionAmount, standardDeductionAmount, pensionDeductionAmount);

        TaxRefund taxRefund = TaxRefund.builder()
                .user(user)
                .calculatedRefund(finalTaxAmount.doubleValue())
                .build();
        taxRefundRepository.save(taxRefund);

        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        String formattedFinalTaxAmount = formatter.format(finalTaxAmount.doubleValue());
        String formattedPensionDeductionAmount = formatter.format(pensionDeductionAmount.doubleValue());

        return RefundResponse.builder()
                .이름(user.getName())
                .결정세액(formattedFinalTaxAmount)
                .퇴직연금세액공제(formattedPensionDeductionAmount)
                .build();
    }

    // 퇴직연금세액공제금액 계산
    private BigDecimal calculatePensionDeductionAmount(UserTaxRefundCalculation calculation) {
        return new BigDecimal(calculation.getTotalPensionContribution().
                replaceAll(",", "")).multiply(new BigDecimal("0.15"));
    }
    // 근로소득세액공제금액 계산
    private BigDecimal calculateIncomeTaxDeduction(BigDecimal calculatedTax) {
        return calculatedTax.multiply(new BigDecimal("0.55"));
    }

    // 특별세액공제금액 계산
    private BigDecimal calculateSpecialDeductionAmount(UserTaxRefundCalculation calculation, BigDecimal totalIncome) {
        // 의료비공제금액 (의료비 - 총지급액 * 3%) * 15%
        BigDecimal medicalExpense = new BigDecimal(calculation.getMedicalExpense()
                .replaceAll(",", ""));
        BigDecimal medicalDeduction = medicalExpense.
                subtract(totalIncome.multiply(new BigDecimal("0.03"))).multiply(new BigDecimal("0.15"));
        // 의료비공제금액이 음수일 경우 0으로 처리
        medicalDeduction = medicalDeduction.max(BigDecimal.ZERO);
        // 보험료공제금액
        BigDecimal insuranceDeduction = new BigDecimal(calculation.getInsuranceExpense().
                replaceAll(",", "")).multiply(new BigDecimal("0.12"));
        // 교육비공제금액
        BigDecimal educationDeduction = new BigDecimal(calculation.getEducationExpense().
                replaceAll(",", "")).multiply(new BigDecimal("0.15"));
        // 기부금공제금액
        BigDecimal donationDeduction = new BigDecimal(calculation.getDonationExpense().
                replaceAll(",", "")).multiply(new BigDecimal("0.15"));

        // 특별세액공제금액 합계
        return insuranceDeduction.add(medicalDeduction).add(educationDeduction).add(donationDeduction);
    }

    // 표준세액공제금액 계산
    private BigDecimal calculateStandardDeductionAmount(BigDecimal specialDeductionAmount) {
        BigDecimal standardDeductionLimit = new BigDecimal("130000");

        if (specialDeductionAmount.compareTo(standardDeductionLimit) < 0) {
            return standardDeductionLimit;
        } else {
            return BigDecimal.ZERO;
        }
    }

    // 결정세액 계산
    private BigDecimal calculateFinalTaxAmount(BigDecimal calculatedTax, BigDecimal incomeTaxDeduction,
                                               BigDecimal specialDeductionAmount, BigDecimal standardDeductionAmount,
                                               BigDecimal pensionDeductionAmount) {
        return calculatedTax.subtract(incomeTaxDeduction)
                .subtract(specialDeductionAmount)
                .subtract(standardDeductionAmount)
                .subtract(pensionDeductionAmount)
                .max(BigDecimal.ZERO);
    }
}
