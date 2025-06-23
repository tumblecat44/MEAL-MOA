package com.dgsw.javasuhangminilet.auth.service;

import com.dgsw.javasuhangminilet.auth.dto.AuthRequest;
import com.dgsw.javasuhangminilet.auth.dto.response.RegisterResponse;
import com.dgsw.javasuhangminilet.auth.entity.UserEntity;
import com.dgsw.javasuhangminilet.auth.repository.AuthRepository;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import com.dgsw.javasuhangminilet.util.ResponseCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;

    public BaseResponse<RegisterResponse> register(AuthRequest authRequest) {
        try {
            // 1. 중복 검사 (name을 username으로 가정)
            if (authRepository.existsByName(authRequest.name())) {
                return BaseResponse.error(ResponseCode.DUPLICATE_RESOURCE, "이미 존재하는 사용자명입니다.");
            }

            // 2. 비밀번호 암호화

            // 3. 토큰 생성 (실제로는 JWT 토큰 생성 로직 사용)
            String token = generateToken(authRequest.name());

            // 4. 사용자 저장
            UserEntity user = UserEntity.builder()
                    .password(authRequest.password())
                    .name(authRequest.name())
                    .build();

            UserEntity savedUser = authRepository.save(user);

            // 5. 응답 생성
            RegisterResponse response = RegisterResponse.builder()
                    .id(savedUser.getId())
                    .name(savedUser.getName())
                    .build();

            return BaseResponse.success(response, "회원가입이 완료되었습니다.");

        } catch (Exception e) {
            // 로그 기록
            log.error("회원가입 중 오류 발생: {}", e.getMessage(), e);
            return BaseResponse.error(ResponseCode.INTERNAL_SERVER_ERROR, "회원가입 처리 중 오류가 발생했습니다.");
        }
    }

    private String generateToken(String username) {
        // 실제로는 JWT 토큰 생성 로직 구현
        // 여기서는 임시로 UUID 사용
        return UUID.randomUUID().toString();
    }

}