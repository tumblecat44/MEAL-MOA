package com.dgsw.javasuhangminilet.auth.service;

import com.dgsw.javasuhangminilet.auth.dto.request.AuthRequest;
import com.dgsw.javasuhangminilet.auth.dto.request.TokenRequest;
import com.dgsw.javasuhangminilet.auth.dto.response.InfoResponse;
import com.dgsw.javasuhangminilet.auth.dto.response.LoginResponse;
import com.dgsw.javasuhangminilet.auth.dto.response.RegisterResponse;
import com.dgsw.javasuhangminilet.auth.entity.UserEntity;
import com.dgsw.javasuhangminilet.auth.repository.AuthRepository;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import com.dgsw.javasuhangminilet.util.JwtTokenProvider;
import com.dgsw.javasuhangminilet.util.ResponseCode;
import com.dgsw.javasuhangminilet.util.TokenClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenClient tokenClient;

    public BaseResponse<RegisterResponse> register(AuthRequest authRequest) {
        // 1. 중복 검사
        if (authRepository.existsByName(authRequest.name())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }

        // 2. 사용자 저장
        UserEntity user = UserEntity.builder()
                .password(passwordEncoder.encode(authRequest.password()))
                .name(authRequest.name())
                .build();

        UserEntity savedUser = authRepository.save(user);


        // 4. 응답 생성
        RegisterResponse response = RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .build();

        return BaseResponse.success(response, "회원가입이 완료되었습니다.");
    }

    public BaseResponse<InfoResponse> info(String token) {
        try {
            Long userId = tokenClient.extractUserIdFromToken(token);
            Optional<UserEntity> user = authRepository.findById(userId);
            
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                return BaseResponse.success(new InfoResponse(userEntity.getName()));
            }
            return BaseResponse.error(ResponseCode.NOT_FOUND, "사용자를 찾을 수 없습니다.");
        } catch (Exception e) {
            log.error("토큰 검증 실패: {}", e.getMessage());
            return BaseResponse.error(ResponseCode.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }
    }

    public BaseResponse<LoginResponse> login(AuthRequest authRequest) {
        Optional<UserEntity> user = authRepository.findByName(authRequest.name());
        if (user.isEmpty()) {
            return BaseResponse.error(ResponseCode.NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }

        UserEntity foundUser = user.get();

        if (passwordEncoder.matches(authRequest.password(), foundUser.getPassword())) {
            // JWT 토큰 생성
            String token = jwtTokenProvider.generateToken(foundUser.getId(), foundUser.getName());
            return BaseResponse.success(new LoginResponse(foundUser.getName(), token));
        }

        return BaseResponse.error(ResponseCode.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }

    public BaseResponse<LoginResponse> changeName(TokenRequest authRequest, String token) {
        try {
            Long userId = tokenClient.extractUserIdFromToken(token);
            Optional<UserEntity> user = authRepository.findById(userId);
            
            if (user.isEmpty()) {
                return BaseResponse.error(ResponseCode.FORBIDDEN, "유효하지 않은 토큰입니다.");
            }

            // 중복 검사
            Optional<UserEntity> existingUser = authRepository.findByName(authRequest.name());
            if (existingUser.isPresent()) {
                return BaseResponse.error(ResponseCode.CONFLICT, "이미 존재하는 사용자명입니다.");
            }

            UserEntity foundUser = user.get();
            foundUser.setName(authRequest.name());
            authRepository.save(foundUser);

            // 새로운 JWT 토큰 생성
            String newToken = jwtTokenProvider.generateToken(foundUser.getId(), foundUser.getName());
            return BaseResponse.success(new LoginResponse(foundUser.getName(), newToken));
        } catch (Exception e) {
            log.error("토큰 검증 실패: {}", e.getMessage());
            return BaseResponse.error(ResponseCode.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }
    }
}