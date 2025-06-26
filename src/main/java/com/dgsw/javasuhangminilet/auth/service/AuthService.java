package com.dgsw.javasuhangminilet.auth.service;

import com.dgsw.javasuhangminilet.auth.dto.request.AuthRequest;
import com.dgsw.javasuhangminilet.auth.dto.request.TokenRequest;
import com.dgsw.javasuhangminilet.auth.dto.response.LoginResponse;
import com.dgsw.javasuhangminilet.auth.dto.response.RegisterResponse;
import com.dgsw.javasuhangminilet.auth.entity.UserEntity;
import com.dgsw.javasuhangminilet.auth.repository.AuthRepository;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import com.dgsw.javasuhangminilet.util.ResponseCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public BaseResponse<RegisterResponse> register(AuthRequest authRequest) {
        // 1. 중복 검사 (name을 username으로 가정)
        if (authRepository.existsByName(authRequest.name())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }

        // 4. 사용자 저장
        UserEntity user = UserEntity.builder()
                .password(passwordEncoder.encode(authRequest.password()))
                .name(authRequest.name())
                .build();

        UserEntity savedUser = authRepository.save(user);

        // 5. 응답 생성
        RegisterResponse response = RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .build();

        return BaseResponse.success(response, "회원가입이 완료되었습니다.");
    }

    public BaseResponse<LoginResponse> info(String token){
        Optional<UserEntity> user = authRepository.findByToken(token);
        if(user.isPresent()){
            UserEntity userEntity = user.get();
            return BaseResponse.success(new LoginResponse(userEntity.getName(), userEntity.getToken()));
        }
        return BaseResponse.error(ResponseCode.NOT_FOUND, "그런 사람 또 없습니다..");
    }

    public BaseResponse<LoginResponse> login(AuthRequest authRequest) {
        Optional<UserEntity> user = authRepository.findByName(authRequest.name());
        if (user.isEmpty()) {
            return BaseResponse.error(ResponseCode.NOT_FOUND, "그런 사람 또 없습니다..");
        }

        UserEntity foundUser = user.get();

        if (passwordEncoder.matches(authRequest.password(), foundUser.getPassword())) {
            return BaseResponse.success(new LoginResponse(foundUser.getName(), foundUser.getToken()));
        }

        return BaseResponse.error(ResponseCode.NOT_FOUND, "Wrong Password.");
    }

    public BaseResponse<LoginResponse> changeName(TokenRequest authRequest, String token) {
        Optional<UserEntity> existingUser = authRepository.findByName(authRequest.name());
        if (existingUser.isPresent()) {
            return BaseResponse.error(ResponseCode.CONFLICT, "이미 사용자가 존재합니다.");
        }
        Optional<UserEntity> user = authRepository.findByToken(token);
        if (user.isEmpty()) {
            return BaseResponse.error(ResponseCode.FORBIDDEN, "당신의 계정이 아닙니다.");
        }

        UserEntity foundUser = user.get();
        foundUser.setName(authRequest.name());
        authRepository.save(foundUser);

        return BaseResponse.success(new LoginResponse(foundUser.getName(), foundUser.getToken()));
    }
}