package com.dgsw.javasuhangminilet.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenClient {
    
    private final JwtTokenProvider jwtTokenProvider;
    
    public static Long getUserIdFromToken(String token) {
        String[] parts = token.split("_");
        if (parts.length >= 2) {
            return Long.parseLong(parts[0]);
        }
        throw new RuntimeException("잘못된 토큰 형식");
    }
    
    /**
     * JWT 또는 레거시 토큰에서 사용자 ID 추출
     */
    public Long extractUserIdFromToken(String token) {
        if (jwtTokenProvider.isLegacyToken(token)) {
            return jwtTokenProvider.getUserIdFromLegacyToken(token);
        } else {
            return jwtTokenProvider.getUserIdFromToken(token);
        }
    }
    
    /**
     * JWT 또는 레거시 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        if (jwtTokenProvider.isLegacyToken(token)) {
            try {
                jwtTokenProvider.getUserIdFromLegacyToken(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return jwtTokenProvider.validateToken(token);
        }
    }
}