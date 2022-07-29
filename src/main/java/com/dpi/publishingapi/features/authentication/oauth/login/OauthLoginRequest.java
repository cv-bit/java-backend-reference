package com.dpi.publishingapi.features.authentication.oauth.login;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.user.User;

public record OauthLoginRequest(User user) implements Command<OauthLoginResponse> {
}
