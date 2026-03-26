package com.ordering.mvc.handler;

import com.ordering.mvc.response.common.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;

@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !ApiResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        int statusCode = HttpStatus.OK.value();

        if (body instanceof ResponseEntity<?> responseEntity) {
            Object innerBody = responseEntity.getBody();
            statusCode = responseEntity.getStatusCode().value();

            if (innerBody instanceof ApiResponse<?>) {
                return innerBody;
            }
            return wrapResponse(innerBody, statusCode);
        }

        if (body == null) {
            return wrapResponse(null, statusCode);
        }

        if (body instanceof ApiResponse<?>) {
            return body;
        }

        return wrapResponse(body, statusCode);
    }

    private <T> ApiResponse<T> wrapResponse(T data, int statusCode) {
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return ApiResponse.<T>builder()
                .status(httpStatus.is2xxSuccessful() ? "success" : "error")
                .message(httpStatus.getReasonPhrase())
                .code(statusCode)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }
}
