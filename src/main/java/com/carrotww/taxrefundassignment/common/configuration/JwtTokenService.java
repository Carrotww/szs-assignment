package com.carrotww.taxrefundassignment.common.configuration;

import com.carrotww.taxrefundassignment.common.service.CustomUserDetailsService;
import com.carrotww.taxrefundassignment.user.dto.UserLoginRequest;
import com.carrotww.taxrefundassignment.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final CustomUserDetailsService userDetailsService;
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration}")
    private int jwtExpirationInMs;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public String generateToken(UserLoginRequest loginRequest) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(loginRequest.getUserId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthenticationServiceException("JWT Token이 만료됐습니다.", e);
        }
    }

    public Authentication getAuthentication(String token) {
        String userId = getUserIdFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String extractJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}