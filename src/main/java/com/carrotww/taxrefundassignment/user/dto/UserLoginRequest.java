package com.carrotww.taxrefundassignment.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequest {
    private String userId;
    private String password;
}
