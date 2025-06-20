package com.mss301.api_gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectResponse {
    private boolean valid;
    private String email;
    private String role;
}