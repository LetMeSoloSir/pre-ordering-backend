package com.ordering.mvc.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BaseException extends RuntimeException {
    private final int code;
    private final String message;
    private final Instant timestamp;

    public BaseException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
        this.timestamp = Instant.now();
    }
}
