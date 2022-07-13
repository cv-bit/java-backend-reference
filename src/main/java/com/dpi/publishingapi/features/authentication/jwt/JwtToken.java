package com.dpi.publishingapi.features.authentication.jwt;

import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class JwtToken {
    private final String token;
    private final String secret;

    public JwtToken(String token, String secret) {
        this.token = token;
        this.secret = secret;
    }

    public static JwtToken generate(String email, String secret, int expirationTime) {
        return new JwtToken(Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact(),
                secret);
    }

    public boolean isValid() {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new CustomException("Invalid Token", HttpStatus.I_AM_A_TEAPOT);
        }
    }

    public String getUsernameFromToken() {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getToken() {
        return token;
    }
}
