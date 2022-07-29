package com.dpi.publishingapi.features.authentication.refresh.refresh_token;

public record RefreshUserTokenResponse(
        String accessToken,
        String refreshToken
) {
}
