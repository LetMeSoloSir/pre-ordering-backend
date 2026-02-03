package com.ordering.mvc.handler;

import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .status("ERROR")
                .message("Internal server error: " + ex.getMessage())
                .code(500)
                .data(null)
                .timestamp(java.time.Instant.now())
                .build();

        return ResponseEntity.status(500).body(response);
    }
}
