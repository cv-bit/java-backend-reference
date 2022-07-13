package com.dpi.publishingapi.features.authentication.refresh;

import com.dpi.publishingapi.data.auth.refresh.RefreshToken;
import com.dpi.publishingapi.data.auth.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class RefreshTokenGenerator {

    private final int refreshExpirationMs;

    @Autowired
    public RefreshTokenGenerator(@Value("${jwtRefreshExpirationMs}") int refreshExpirationMs) {
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public RefreshToken generate(User user) {
        return new RefreshToken(
                user,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(refreshExpirationMs));
    }
}
