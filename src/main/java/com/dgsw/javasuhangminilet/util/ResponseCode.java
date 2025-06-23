package com.dgsw.javasuhangminilet.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    // 성공
    SUCCESS("200", "성공"),

    // 클라이언트 에러
    BAD_REQUEST("400", "잘못된 요청입니다."),
    UNAUTHORIZED("401", "인증이 필요합니다."),
    FORBIDDEN("403", "접근 권한이 없습니다."),
    NOT_FOUND("404", "요청한 리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED("405", "허용되지 않은 HTTP 메서드입니다."),
    VALIDATION_ERROR("422", "입력값 검증에 실패했습니다."),

    // 서버 에러
    INTERNAL_SERVER_ERROR("500", "서버 내부 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE("503", "서비스를 사용할 수 없습니다."),

    // 비즈니스 로직 에러
    DUPLICATE_RESOURCE("1001", "이미 존재하는 리소스입니다."),
    RESOURCE_NOT_FOUND("1002", "리소스를 찾을 수 없습니다."),
    INSUFFICIENT_PERMISSION("1003", "권한이 부족합니다."),
    BUSINESS_RULE_VIOLATION("1004", "비즈니스 규칙 위반입니다.");

    private final String code;
    private final String message;
}
