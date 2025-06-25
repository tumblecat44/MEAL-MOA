package com.dgsw.javasuhangminilet.auth.controller;

import com.dgsw.javasuhangminilet.auth.dto.request.AuthRequest;
import com.dgsw.javasuhangminilet.auth.dto.request.TokenRequest;
import com.dgsw.javasuhangminilet.auth.dto.response.LoginResponse;
import com.dgsw.javasuhangminilet.auth.dto.response.RegisterResponse;
import com.dgsw.javasuhangminilet.auth.service.AuthService;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public BaseResponse<RegisterResponse> register(AuthRequest authRequest) {
        return authService.register(authRequest);
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PatchMapping("/change-name")
    public BaseResponse<LoginResponse> changeName(TokenRequest tokenRequest) {
        return authService.changeName(tokenRequest);
    }
}
