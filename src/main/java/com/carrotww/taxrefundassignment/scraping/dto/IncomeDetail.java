package com.carrotww.taxrefundassignment.scraping.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncomeDetail {
    private String 소득내역;
    private String 총지급액;
    private String 업무시작일;
    private String 기업명;
    private String 이름;
    private String 지급일;
    private String 업무종료일;
    private String 주민등록번호;
    private String 소득구분;
    private String 사업자등록번호;

    public String get소득내역() {
        return 소득내역;
    }

    public String get총지급액() {
        return 총지급액;
    }
}
