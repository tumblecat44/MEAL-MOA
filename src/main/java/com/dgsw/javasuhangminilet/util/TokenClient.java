package com.dgsw.javasuhangminilet.util;

import org.springframework.context.annotation.Bean;


public class TokenClient {
    @Bean
    public Long getUserIdFromToken(String token) {
        String[] parts = token.split("_");
        if (parts.length >= 2) {
            return Long.parseLong(parts[0]);
        }
        throw new RuntimeException("잘못된 토큰 형식");
    }
}
