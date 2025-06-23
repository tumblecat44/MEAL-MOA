package com.dgsw.javasuhangminilet.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 통합 API 응답 클래스
 * 모든 API 응답의 표준 형태를 정의합니다.
 *
 * @param <T> 응답 데이터의 타입
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    /**
     * 요청 성공 여부
     */
    @JsonProperty("success")
    private boolean success;

    /**
     * 응답 메시지
     */
    @JsonProperty("message")
    private String message;

    /**
     * 응답 코드 (비즈니스 로직용)
     */
    @JsonProperty("code")
    private String code;

    /**
     * 응답 데이터
     */
    @JsonProperty("data")
    private T data;

    /**
     * 응답 생성 시간
     */
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    /**
     * 에러 상세 정보 (실패시에만)
     */
    @JsonProperty("errors")
    private List<ErrorDetail> errors;

    // ===== 정적 팩토리 메서드들 =====

    /**
     * 성공 응답 (데이터 포함)
     */
    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .success(true)
                .message("요청이 성공적으로 처리되었습니다.")
                .code(ResponseCode.SUCCESS.getCode())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 성공 응답 (데이터 + 커스텀 메시지)
     */
    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .code(ResponseCode.SUCCESS.getCode())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 성공 응답 (데이터 없음)
     */
    public static BaseResponse<Void> success() {
        return BaseResponse.<Void>builder()
                .success(true)
                .message("요청이 성공적으로 처리되었습니다.")
                .code(ResponseCode.SUCCESS.getCode())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 실패 응답 (기본)
     */
    public static <T> BaseResponse<T> error(String message) {
        return BaseResponse.<T>builder()
                .success(false)
                .message(message)
                .code(ResponseCode.INTERNAL_SERVER_ERROR.getCode())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 실패 응답 (코드 + 메시지)
     */
    public static <T> BaseResponse<T> error(ResponseCode responseCode) {
        return BaseResponse.<T>builder()
                .success(false)
                .message(responseCode.getMessage())
                .code(responseCode.getCode())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 실패 응답 (코드 + 커스텀 메시지)
     */
    public static <T> BaseResponse<T> error(ResponseCode responseCode, String customMessage) {
        return BaseResponse.<T>builder()
                .success(false)
                .message(customMessage)
                .code(responseCode.getCode())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 검증 실패 응답 (필드별 에러 포함)
     */
    public static <T> BaseResponse<T> validationError(List<ErrorDetail> errors) {
        return BaseResponse.<T>builder()
                .success(false)
                .message("입력값 검증에 실패했습니다.")
                .code(ResponseCode.VALIDATION_ERROR.getCode())
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ===== 편의 메서드들 =====

    /**
     * 성공 응답인지 확인
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * 실패 응답인지 확인
     */
    public boolean isError() {
        return !this.success;
    }

    /**
     * 데이터가 존재하는지 확인
     */
    public boolean hasData() {
        return this.data != null;
    }

    /**
     * 에러 상세가 존재하는지 확인
     */
    public boolean hasErrors() {
        return this.errors != null && !this.errors.isEmpty();
    }

    // ===== 내부 클래스들 =====

    /**
     * 에러 상세 정보
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetail {

        @JsonProperty("field")
        private String field;

        @JsonProperty("message")
        private String message;

        @JsonProperty("rejectedValue")
        private Object rejectedValue;

        public static ErrorDetail of(String field, String message) {
            return ErrorDetail.builder()
                    .field(field)
                    .message(message)
                    .build();
        }

        public static ErrorDetail of(String field, String message, Object rejectedValue) {
            return ErrorDetail.builder()
                    .field(field)
                    .message(message)
                    .rejectedValue(rejectedValue)
                    .build();
        }
    }
}
