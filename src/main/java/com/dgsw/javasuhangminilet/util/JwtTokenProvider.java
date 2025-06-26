package com.dgsw.javasuhangminilet.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-validity}")
    private long jwtAccessTokenValidity;

    @Value("${jwt.refresh-token-validity}")
    private long jwtRefreshTokenValidity;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * JWT 토큰 생성
     */
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtAccessTokenValidity);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * JWT 토큰에서 사용자 ID 추출
     */
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return Long.parseLong(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT 토큰에서 사용자 ID 추출 실패: {}", e.getMessage());
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    /**
     * JWT 토큰에서 사용자명 추출
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.get("username", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT 토큰에서 사용자명 추출 실패: {}", e.getMessage());
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    /**
     * JWT 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT 토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 기존 토큰 형식과의 호환성을 위한 메서드
     * 기존: "userId_UUID" 형식
     * 새로운: JWT 토큰
     */
    public boolean isLegacyToken(String token) {
        return token != null && token.contains("_") && !token.startsWith("eyJ");
    }

    /**
     * 레거시 토큰에서 사용자 ID 추출 (기존 로직과의 호환성)
     */
    public Long getUserIdFromLegacyToken(String token) {
        if (!isLegacyToken(token)) {
            throw new RuntimeException("레거시 토큰 형식이 아닙니다.");
        }
        
        String[] parts = token.split("_");
        if (parts.length >= 2) {
            return Long.parseLong(parts[0]);
        }
        throw new RuntimeException("잘못된 토큰 형식");
    }
} 