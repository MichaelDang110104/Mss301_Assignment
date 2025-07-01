package com.mss301.api_gateway.filter;


import com.mss301.api_gateway.dtos.ApiResponse;
import com.mss301.api_gateway.exception.GatewayException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final String[] PUBLIC_URL = {
            "/user-service/users/login",
            "/user-service/users/register-account",
            "/user-service/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/user-service/users/authen-jwt",
            "/car-service/swagger-ui.html",
            "/rental-service/swagger-ui.html",
            "/car-service/api/cars/get-all-cars",
            "/car-service/api/cars/get-car/**"
    };

    private static final String JWT_VALIDATOR_PATH = "/users/authen-jwt";

    private final PathPatternParser patternParser = new PathPatternParser();

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (isPublic(request)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new GatewayException(401, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        WebClient webClient = WebClient.builder()
                .baseUrl("http://84.247.149.231:8010")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(JWT_VALIDATOR_PATH)
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Map<String, Object>>>() {})
                .flatMap(apiResponse -> {
                    log.debug("Api response: {}", apiResponse);
                    Map<String, Object> userInfo = apiResponse.getData();
                    if (userInfo == null || !userInfo.containsKey("email") || !userInfo.containsKey("role")) {
                        throw new GatewayException(401, "Invalid token data", HttpStatus.UNAUTHORIZED);
                    }

                    ServerHttpRequest mutatedRequest = request
                            .mutate()
                            .header("X-User-Email", userInfo.get("email").toString())
                            .header("X-User-Role", userInfo.get("role").toString())
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                })
                .onErrorResume(e -> {
                    log.error("Token validation failed: {}", e.getMessage());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    private boolean isPublic(ServerHttpRequest request) {
        PathContainer pathContainer = request.getPath().pathWithinApplication();
        return Arrays.stream(PUBLIC_URL)
                .map(patternParser::parse)
                .anyMatch(pattern -> pattern.matches(pathContainer));
    }
}
