package com.dgsw.javasuhangminilet.util;



public class TokenClient {
    public static Long getUserIdFromToken(String token) {
        String[] parts = token.split("_");
        if (parts.length >= 2) {
            return Long.parseLong(parts[0]);
        }
        throw new RuntimeException("잘못된 토큰 형식");
    }
}