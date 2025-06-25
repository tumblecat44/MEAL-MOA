package com.dgsw.javasuhangminilet.review.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO {
    private Long userId;
    private String title;
    private String content;
}
