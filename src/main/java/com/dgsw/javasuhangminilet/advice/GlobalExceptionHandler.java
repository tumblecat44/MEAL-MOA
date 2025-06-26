package com.dgsw.javasuhangminilet.advice;

import com.dgsw.javasuhangminilet.util.BaseResponse;
import com.dgsw.javasuhangminilet.util.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<?>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("IllegalArgument: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(BaseResponse.error(ResponseCode.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<BaseResponse<?>> handleRuntime(RuntimeException ex) {
        log.error("RuntimeException: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(BaseResponse.error(ResponseCode.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleGeneral(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.internalServerError().body(BaseResponse.error(ResponseCode.INTERNAL_SERVER_ERROR, "예상치 못한 오류가 발생했습니다."));
    }
}