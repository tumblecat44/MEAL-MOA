package com.dgsw.javasuhangminilet.review.dto.response;

import com.dgsw.javasuhangminilet.auth.entity.UserEntity;
import jakarta.persistence.*;

public record ReviewResponse(
        Long id, Long user, String title, String content
) {
}