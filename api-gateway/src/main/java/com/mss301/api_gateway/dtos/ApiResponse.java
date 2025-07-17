package com.mss301.api_gateway.dtos;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int code;

    private String message;

    private T data;
}
