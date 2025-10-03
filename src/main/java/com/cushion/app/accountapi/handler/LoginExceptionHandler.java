package com.cushion.app.accountapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class LoginExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // 예외 결과에서 모든 필드 에러 정보를 가져옵니다.
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // 필드 에러 정보에서 필드명과 에러 메시지를 추출합니다.
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // 에러 정보를 담은 Map을 400 Bad Request 상태와 함께 응답합니다.
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
