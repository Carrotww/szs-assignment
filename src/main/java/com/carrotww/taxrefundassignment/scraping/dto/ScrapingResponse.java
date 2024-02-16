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