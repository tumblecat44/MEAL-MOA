package com.dgsw.javasuhangminilet.auth.repository;

import com.dgsw.javasuhangminilet.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByName(String name);

    Optional<UserEntity> findByName(String name);
}