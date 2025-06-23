package com.dgsw.javasuhangminilet.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "이름은 필수입니다.")
        @Size(min = 2, max = 50, message = "이름은 2-50자 사이여야 합니다.")
        String name
        ,
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 2, max = 20, message = "비밀번호는 8-20자 사이여야 합니다.")
        String password


) {}