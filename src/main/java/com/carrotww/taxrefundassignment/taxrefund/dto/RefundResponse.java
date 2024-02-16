package com.carrotww.taxrefundassignment.taxrefund.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundResponse {
    private String 이름;
    private String 결정세액;
    private String 퇴직연금세액공제;
}