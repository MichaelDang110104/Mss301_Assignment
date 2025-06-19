    package com.mss301.api_gatewate.utils;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
//    import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//    import org.springframework.security.oauth2.jwt.JwtDecoder;
//    import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

    import javax.crypto.spec.SecretKeySpec;

//    @Configuration
    public class JwtConfig {
        @Value("${jwt.secret-key}")
        private String secretKey;
//
//        @Bean
//        public JwtDecoder jwtDecoder() {
//            return NimbusJwtDecoder.withSecretKey(
//                    new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")
//            ).build();
//        }

    }
