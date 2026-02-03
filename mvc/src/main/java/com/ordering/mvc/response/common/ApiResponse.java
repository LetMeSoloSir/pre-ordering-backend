package com.ordering.mvc.response.common;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private String status;
    private String message;
    private int code;
    private T data;
    private Instant timestamp =  Instant.now();
}
