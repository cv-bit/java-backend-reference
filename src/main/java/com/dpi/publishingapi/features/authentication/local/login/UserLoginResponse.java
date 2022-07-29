package com.dpi.publishingapi.features.authentication.local.login;

public record UserLoginResponse(
        String accessToken,
        String refreshToken,
        String email
) {
}
