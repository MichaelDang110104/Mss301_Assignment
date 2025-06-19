//package com.mss301.api_gatewate.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//
//import java.util.List;
//
//public class AuthGlobalFilter implements GlobalFilter, Ordered {
//    private final JwtDecoder jwtDecoder;
//
//    public AuthGlobalFilter(JwtDecoder jwtDecoder) {
//        this.jwtDecoder = jwtDecoder;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        List<String> authHeaders = exchange.getRequest().getHeaders().get("Authorization");
//
//        if (authHeaders != null && !authHeaders.isEmpty()) {
//            String authHeader = authHeaders.get(0);
//
//            if (authHeader.startsWith("Bearer ")) {
//                String token = authHeader.substring(7);
//
//                try {
//                    Jwt jwt = jwtDecoder.decode(token);
//
//                    String uid = jwt.getSubject();
//                    String email = jwt.getClaim("email");
//                    String role = jwt.getClaim("role");
//
//                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                            .header("X-User-Id", uid)
//                            .header("X-User-Email", email != null ? email : "")
//                            .header("X-User-Role", role != null ? role : "CUSTOMER")
//                            .build();
//
//                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
//                } catch (JwtException e) {
//                    e.printStackTrace();
//                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                    return exchange.getResponse().setComplete();
//                }
//            } else {
//                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
//                return exchange.getResponse().setComplete();
//            }
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//}
