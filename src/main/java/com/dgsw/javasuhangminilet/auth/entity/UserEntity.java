package com.dgsw.javasuhangminilet.auth.entity;

import com.dgsw.javasuhangminilet.review.entity.ReviewEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


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

    @Column(length = 500)
    private String token;

    @OneToMany
    private List<ReviewEntity> reviews;

    // 비즈니스 로직용 생성자
    public UserEntity(String password, String name, String token) {
        this.password = password;
        this.name = name;
        this.token = token;
    }
    @PostPersist
    public void generateToken() {
        if (this.id != null && this.token == null) {
            this.token = this.id + "_" + UUID.randomUUID().toString();
        }
    }
}
