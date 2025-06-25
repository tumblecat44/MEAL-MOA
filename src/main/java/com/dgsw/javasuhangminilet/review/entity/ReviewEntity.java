package com.dgsw.javasuhangminilet.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="reviews")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 ID 증가
    private Long id;

    private Long userId;
    private String title;
    private String content;
}