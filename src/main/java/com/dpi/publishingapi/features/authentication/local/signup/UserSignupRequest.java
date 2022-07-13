package com.dpi.publishingapi.features.authentication.local.signup;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserSignupRequest(
        @NotBlank
        @Size(max = 50)
        @Email
        String email,
        @NotBlank
        @Size(max = 120, min = 8)
        String password
) implements Command<UserSignupResponse> {
}
