package com.dpi.publishingapi.auth.refresh;

import com.dpi.publishingapi.data.auth.refresh.RefreshToken;
import com.dpi.publishingapi.data.auth.user.LoginMethod;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.features.authentication.refresh.RefreshTokenGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RefreshTokenGeneratorUnit {

    @Test
    public void testRefreshTokenGeneration() {
        RefreshTokenGenerator generator = new RefreshTokenGenerator(10000);
        User user = new User("testemail@test.com", "password", new HashSet<>(), null, true, LoginMethod.LOCAL);
        RefreshToken token = generator.generate(user);

        assertNotNull(token.getToken());
        assertEquals(user, token.getUser());
        assertTrue(token.getExpirationDate().isAfter(Instant.now()));
    }
}
