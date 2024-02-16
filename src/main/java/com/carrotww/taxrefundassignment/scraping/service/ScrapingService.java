package com.carrotww.taxrefundassignment.scraping.service;

import com.carrotww.taxrefundassignment.scraping.dto.*;
import com.carrotww.taxrefundassignment.taxrefund.Repository.TaxRefundRepository;
import com.carrotww.taxrefundassignment.user.entity.User;
import com.carrotww.taxrefundassignment.user.entity.UserTaxRefundCalculation;
import com.carrotww.taxrefundassignment.user.repository.UserRepository;
import com.carrotww.taxrefundassignment.user.repository.UserTaxRefundCalculationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Transactional
@RequiredArgsConstructor
public class ScrapingService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final UserTaxRefundCalculationRepository userTaxRefundCalculationRepository;
    private static final String BASEURL = "https://codetest.3o3.co.kr";
    private static final String SCRAPVERSION = "/v2/scrap";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public ScrapingResponse scrapeIncomeInfo(String userId, String token) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저아이디가 존재하지 않습니다 : " + userId));

        ScrapingRequest request = ScrapingRequest.builder()
                .name(user.getName())
                .regNo(user.getRegNo())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, BEARER + token);

        HttpEntity<ScrapingRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ScrapingResponse> response = restTemplate.postForEntity(BASEURL + SCRAPVERSION,
                entity, ScrapingResponse.class);

        ScrapingResponse scrapingResponse = response.getBody();

        if (scrapingResponse != null && scrapingResponse.getData() != null) {
            saveTaxRefundCalculation(user, scrapingResponse.getData());
        }

        return scrapingResponse;
    }

    private void saveTaxRefundCalculation(User user, Data data) {
        JsonList jsonList = data.getJsonList();

        // 총 소득
        String totalIncome = jsonList.get급여().stream()
                .map(IncomeDetail::get총지급액)
                .reduce("", String::concat);

        String calculatedTax = jsonList.get산출세액();

        String medicalExpense = getDeductionAmount(jsonList, "의료비");
        String educationExpense = getDeductionAmount(jsonList, "교육비");
        String donationExpense = getDeductionAmount(jsonList, "기부금");
        String insuranceExpense = getDeductionAmount(jsonList, "보험료");
        String totalPensionContribution = getPensionAmount(jsonList, "퇴직연금");

        UserTaxRefundCalculation calculation = UserTaxRefundCalculation.builder()
                .user(user)
                .totalIncome(totalIncome)
                .calculatedTax(calculatedTax)
                .medicalExpense(medicalExpense)
                .educationExpense(educationExpense)
                .donationExpense(donationExpense)
                .insuranceExpense(insuranceExpense)
                .totalPensionContribution(totalPensionContribution)
                .build();

        userTaxRefundCalculationRepository.save(calculation);
    }

    private String getDeductionAmount(JsonList jsonList, String deductionType) {
        return jsonList.get소득공제().stream()
                .filter(deductionDetail -> deductionType.equals(deductionDetail.get소득구분()))
                .map(DeductionDetail::get금액)
                .findFirst()
                .orElse("");
    }

    private String getPensionAmount(JsonList jsonList, String deductionType) {
        return jsonList.get소득공제().stream()
                .filter(deductionDetail -> deductionType.equals(deductionDetail.get소득구분()))
                .map(DeductionDetail::get총납임금액)
                .findFirst()
                .orElse("");
    }
}