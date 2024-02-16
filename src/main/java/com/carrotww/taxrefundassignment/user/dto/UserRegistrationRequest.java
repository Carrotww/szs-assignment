package com.carrotww.taxrefundassignment.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegistrationRequest {
    private String userId;
    private String password;
    private String name;
    private String regNo;
}
