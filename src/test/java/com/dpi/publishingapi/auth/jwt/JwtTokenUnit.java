package com.dpi.publishingapi.auth.jwt;


import com.dpi.publishingapi.features.authentication.jwt.JwtToken;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtTokenUnit {

    private String email;
    private String secret;
    private JwtToken token;

    @BeforeEach
    public void setup() {
        email = "testemail@test.com";
        secret = "testkey";
        token = JwtToken.generate(email, secret, 1000000);
    }


    @Test
    public void testGeneratedTokenIsValid() {
        String subject =
                Jwts.parser().setSigningKey(secret).parseClaimsJws(token.getToken()).getBody().getSubject();

        assertEquals(email, subject);
    }

    @Test
    public void testIsValid() {
        boolean isValid = token.isValid();
        assertTrue(isValid);
    }

    @Test
    public void testIsValidThrowsExceptionWhenExpired() {
        JwtToken expiredToken = JwtToken.generate(email, secret, -1000);

        assertThrows(CustomException.class, () -> expiredToken.isValid());
    }

    @Test
    public void testGetUsernameFromTokenReturnsEmail() {
        String username = token.getUsernameFromToken();

        assertEquals(username, email);
    }

}
