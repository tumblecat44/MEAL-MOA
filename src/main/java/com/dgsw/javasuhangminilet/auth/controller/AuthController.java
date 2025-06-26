package com.dgsw.javasuhangminilet.auth.controller;

import com.dgsw.javasuhangminilet.auth.dto.request.AuthRequest;
import com.dgsw.javasuhangminilet.auth.dto.request.TokenRequest;
import com.dgsw.javasuhangminilet.auth.dto.response.LoginResponse;
import com.dgsw.javasuhangminilet.auth.dto.response.RegisterResponse;
import com.dgsw.javasuhangminilet.auth.service.AuthService;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import com.dgsw.javasuhangminilet.util.ResponseCode;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
    public BaseResponse<LoginResponse> changeName(TokenRequest tokenRequest, @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null) {
            return BaseResponse.error(ResponseCode.UNAUTHORIZED, "토큰을 넣어주세요");
        }
        return authService.changeName(tokenRequest, token);
    }

    @GetMapping("/info")
    public BaseResponse<LoginResponse> info(@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null) {
            return BaseResponse.error(ResponseCode.UNAUTHORIZED, "토큰을 넣어주세요");
        }
        return authService.info(token);
    }
}
