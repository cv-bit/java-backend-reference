package com.dpi.publishingapi.features.authentication.oauth.signup;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record OauthSignupRequest(
        @NotBlank
        @Size(max = 50)
        @Email
        String email
) implements Command<OauthSignupResponse> {
}
