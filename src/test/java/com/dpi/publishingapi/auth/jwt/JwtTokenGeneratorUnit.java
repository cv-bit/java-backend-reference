package com.dpi.publishingapi.auth.jwt;

import com.dpi.publishingapi.features.authentication.jwt.JwtTokenGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtTokenGeneratorUnit {

    @Test
    public void testGenerateWorks() {
        JwtTokenGenerator tokenGenerator = new JwtTokenGenerator("secret", 1000);
        String token = tokenGenerator.generate("testemail@test.com");
        assertNotNull(token);
    }
}
