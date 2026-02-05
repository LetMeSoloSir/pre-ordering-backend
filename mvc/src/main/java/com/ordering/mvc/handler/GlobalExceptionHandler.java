package com.ordering.mvc.handler;

import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Object>> handleBaseException(BaseException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .status("ERROR")
                .message(ex.getMessage())
                .code(ex.getCode())
                .data(null)
                .timestamp(ex.getTimestamp())
                .build();

        return ResponseEntity.status(ex.getCode()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .status("ERROR")
                .message("Access Denied: " + ex.getMessage())
                .code(403)
                .data(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthentication(AuthenticationException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .status("ERROR")
                .message("Unauthorized: " + ex.getMessage())
                .code(401)
                .data(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .status("ERROR")
                .message("Internal server error: " + ex.getMessage())
                .code(500)
                .data(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(500).body(response);
    }
}
