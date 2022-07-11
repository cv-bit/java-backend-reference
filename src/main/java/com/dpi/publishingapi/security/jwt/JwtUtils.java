package com.dpi.publishingapi.security.jwt;

import com.dpi.publishingapi.exceptions.CustomException;
import com.dpi.publishingapi.security.user.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private final String jwtSecret;

    private final int jwtExpirationMs;

    private final int jwtRefreshExpirationMs;

    public JwtUtils(@Value("${jwtSecret}") String jwtSecret, @Value("${jwtExpirationMs}") int jwtExpirationMs, @Value("${jwtRefreshExpirationMs}") int jwtRefreshExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;
    }

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return generateJwtToken(userPrincipal.getUsername());
    }

    public String generateJwtToken(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateToken(String email) {
        return generateJwtToken(email);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new CustomException("Invalid Token", HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
