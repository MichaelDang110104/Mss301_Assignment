package su25_se183660.userservice.commons;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import su25_se183660.userservice.pojos.User;

import java.time.Instant;

@Component
public class JWTUtils {
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Autowired
    private JwtDecoder jwtDecoder;

    public String getUserFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    public Instant getJWTExpiration(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return Instant.from(jwt.getExpiresAt());
    }

    public String createJwtToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24 * 3600))
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .build();
        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return encoder.encode(params).getTokenValue();
    }

}
