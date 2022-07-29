package com.dpi.publishingapi.features.authentication.local.verification;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotNull;

public record UserVerificationRequest(@NotNull Long verificationCode) implements Command<UserVerificationResponse> {
}
