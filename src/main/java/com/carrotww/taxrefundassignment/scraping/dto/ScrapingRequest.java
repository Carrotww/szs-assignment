package com.carrotww.taxrefundassignment.scraping.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapingRequest {
    private String name;
    private String regNo;
}
