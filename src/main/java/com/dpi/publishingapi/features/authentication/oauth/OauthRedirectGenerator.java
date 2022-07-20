package com.dpi.publishingapi.features.authentication.oauth;

import com.dpi.publishingapi.data.auth.refresh.RefreshToken;
import com.dpi.publishingapi.data.auth.refresh.RefreshTokenRepository;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.features.authentication.jwt.JwtTokenGenerator;
import com.dpi.publishingapi.features.authentication.refresh.RefreshTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OauthRedirectGenerator {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public OauthRedirectGenerator(JwtTokenGenerator jwtTokenGenerator, RefreshTokenGenerator refreshTokenGenerator, RefreshTokenRepository refreshTokenRepository) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.refreshTokenGenerator = refreshTokenGenerator;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateUrl(User user) {
        String token = jwtTokenGenerator.generate(user.getEmail());
        RefreshToken refreshToken = refreshTokenGenerator.generate(user);
        refreshTokenRepository.save(refreshToken);

        String url = UriComponentsBuilder.fromUriString("http://localhost:3000/login/oauth/redirect")
                .queryParam("token", token)
                .queryParam("refresh", refreshToken.getToken())
                .queryParam("email", user.getEmail())
                .build().toUriString();

        return url;
    }
}
