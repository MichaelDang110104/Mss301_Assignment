package com.mss301.api_gateway.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class GatewayException extends RuntimeException {
    private int code;
    private String message;
    private HttpStatusCode statusCode;

    public GatewayException(int code, String message, HttpStatusCode statusCode) {
        super(message);
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}