package com.carrotww.taxrefundassignment.scraping.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JsonList {
    private List<IncomeDetail> 급여;
    private String 산출세액;
    private List<DeductionDetail> 소득공제;

    public List<IncomeDetail> get급여() {
        return 급여;
    }

    public String get산출세액() {
        return 산출세액;
    }

    public List<DeductionDetail> get소득공제() {
        return 소득공제;
    }
}

