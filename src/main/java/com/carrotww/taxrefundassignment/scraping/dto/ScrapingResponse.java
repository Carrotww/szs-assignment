package com.carrotww.taxrefundassignment.scraping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapingResponse {
    private String status;
    private Data data;
    private Map<String, Object> errors;
}

//@Getter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class ScrapingResponse {
//    private String status;
//    private Data data;
//    private Map<String, Object> errors;
//
//    @Getter
//    @Builder
//    @AllArgsConstructor(access = AccessLevel.PROTECTED)
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    public static class Data {
//        @JsonProperty("jsonList")
//        private JsonList jsonList;
//        private String appVer;
//        private String errMsg;
//        private String company;
//        private String svcCd;
//        private String hostNm;
//        private String workerResDt;
//        private String workerReqDt;
//    }
//
//    @Getter
//    @Builder
//    @AllArgsConstructor(access = AccessLevel.PROTECTED)
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    public static class JsonList {
//        private List<IncomeDetail> 급여;
//        private String 산출세액;
//        private List<DeductionDetail> 소득공제;
//    }
//
//    @Getter
//    @Builder
//    @AllArgsConstructor(access = AccessLevel.PROTECTED)
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    public static class IncomeDetail {
//        private String 소득내역;
//        private String 총지급액;
//        private String 업무시작일;
//        private String 기업명;
//        private String 이름;
//        private String 지급일;
//        private String 업무종료일;
//        private String 주민등록번호;
//        private String 소득구분;
//        private String 사업자등록번호;
//    }
//
//    @Getter
//    @Builder
//    @AllArgsConstructor(access = AccessLevel.PROTECTED)
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    public static class DeductionDetail {
//        private String 금액;
//        private String 소득구분;
//    }
//}
