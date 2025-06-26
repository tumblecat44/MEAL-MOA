package com.dgsw.javasuhangminilet.auth.entity;

import com.dgsw.javasuhangminilet.review.entity.ReviewEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String name;
}
