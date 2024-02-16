package com.carrotww.taxrefundassignment.scraping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeductionDetail {
    private String 금액;
    private String 소득구분;
    private String 총납임금액;
    public String get금액() {
        return 금액;
    }

    public String get총납임금액() {
        return 총납임금액;
    }

    public String get소득구분() {
        return 소득구분;
    }
}
