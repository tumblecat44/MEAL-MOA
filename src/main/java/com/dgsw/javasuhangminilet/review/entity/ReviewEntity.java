package com.dgsw.javasuhangminilet.review.entity;

import com.dgsw.javasuhangminilet.auth.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 ID 증가
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

}