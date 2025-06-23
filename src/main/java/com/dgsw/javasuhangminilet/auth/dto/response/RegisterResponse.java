package com.dgsw.javasuhangminilet.auth.dto.response;

import lombok.Builder;

@Builder
public record RegisterResponse(
        Long id,
        String name
) {}