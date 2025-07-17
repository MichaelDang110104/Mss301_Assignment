package com.mss301.api_gateway.exception;

import com.mss301.api_gateway.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = GatewayException.class)
    ResponseEntity<ApiResponse<Void>> handlingGatewayException(GatewayException exception) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setCode(exception.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(exception.getStatusCode()).body(apiResponse);
    }

}