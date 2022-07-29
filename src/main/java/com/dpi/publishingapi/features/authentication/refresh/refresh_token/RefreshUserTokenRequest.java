package com.dpi.publishingapi.features.authentication.refresh.refresh_token;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotBlank;

public record RefreshUserTokenRequest(@NotBlank String refreshToken) implements Command<RefreshUserTokenResponse> {
}
