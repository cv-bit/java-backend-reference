package com.dpi.publishingapi;


import com.dpi.publishingapi.security.jwt.JwtUtils;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtUtilsUnit {

    @Test
    public void testGenerateToken() {
        String testKey = "testkey";
        JwtUtils jwtUtils = new JwtUtils(testKey, 1000000000, 100000000);
        String email = "testemail@test.com";
        String token = jwtUtils.generateJwtToken(email);
        String subject =
                Jwts.parser().setSigningKey(testKey).parseClaimsJws(token).getBody().getSubject();
        Assertions.assertEquals(email, subject);
    }
}
