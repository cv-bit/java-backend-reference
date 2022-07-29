package com.dpi.publishingapi.features.authentication.refresh.refresh_token;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.refresh.RefreshToken;
import com.dpi.publishingapi.data.auth.refresh.RefreshTokenRepository;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.features.authentication.jwt.JwtTokenGenerator;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RefreshUserTokenHandler implements Command.Handler<RefreshUserTokenRequest, RefreshUserTokenResponse> {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    public RefreshUserTokenHandler(RefreshTokenRepository refreshTokenRepository, JwtTokenGenerator jwtTokenGenerator) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    private boolean isExpired(RefreshToken token) {
        return token.getExpirationDate().compareTo(Instant.now()) <= 0;
    }

    @Override
    public RefreshUserTokenResponse handle(RefreshUserTokenRequest refreshUserTokenRequest) {
        String token = refreshUserTokenRequest.refreshToken();

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new CustomException("Refresh token doesn't exist.", HttpStatus.NOT_FOUND));

        if (isExpired(refreshToken)) {
            refreshTokenRepository.delete(refreshToken);
            throw new CustomException("Refresh token is expired.  You will have to sign in again", HttpStatus.BAD_REQUEST);
        }

        User user = refreshToken.getUser();
        String newToken = jwtTokenGenerator.generate(user.getEmail());

        return new RefreshUserTokenResponse(newToken, token);
    }
}
