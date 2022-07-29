package com.dpi.publishingapi.features.authentication.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator {

    private final String jwtSecret;
    private final int jwtExpirationMs;

    @Autowired
    public JwtTokenGenerator(@Value("${jwtSecret}") String jwtSecret, @Value("${jwtExpirationMs}") int jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generate(String email) {
        return JwtToken
                .generate(email, jwtSecret, jwtExpirationMs)
                .getToken();
    }
}
