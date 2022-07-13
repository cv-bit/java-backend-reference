package com.dpi.publishingapi.features.authentication.local.login;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotBlank;

public record UserLoginRequest(@NotBlank String email, @NotBlank String password) implements Command<UserLoginResponse> {
}
