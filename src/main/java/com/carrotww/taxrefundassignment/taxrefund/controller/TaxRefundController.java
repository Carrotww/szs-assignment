package com.carrotww.taxrefundassignment.taxrefund.controller;

import com.carrotww.taxrefundassignment.common.configuration.JwtTokenService;
import com.carrotww.taxrefundassignment.scraping.dto.ScrapingResponse;
import com.carrotww.taxrefundassignment.scraping.service.ScrapingService;
import com.carrotww.taxrefundassignment.taxrefund.dto.RefundResponse;
import com.carrotww.taxrefundassignment.taxrefund.service.TaxRefundService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/szs")
public class TaxRefundController {
    private final ScrapingService scrapingService;
    private final JwtTokenService jwtTokenService;
    private final TaxRefundService taxRefundService;

    @PostMapping("/scrap")
    public ResponseEntity<ScrapingResponse> scrapeIncomeInfo(HttpServletRequest request, Authentication authentication) {
        String userId = authentication.getName();
        String token = jwtTokenService.extractJwtTokenFromRequest(request);

        return ResponseEntity.ok(scrapingService.scrapeIncomeInfo(userId, token));
    }

    @GetMapping("/refund")
    public ResponseEntity<RefundResponse> getRefund(Authentication authentication) {
        String userId = authentication.getName();
        System.out.print("@@@@@@@@@@@@@@@");
        System.out.println(userId);
        RefundResponse refundResponse = taxRefundService.calculateRefund(userId);
        return ResponseEntity.ok(refundResponse);
    }
}