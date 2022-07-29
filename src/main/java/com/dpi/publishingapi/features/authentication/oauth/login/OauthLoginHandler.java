package com.dpi.publishingapi.features.authentication.oauth.login;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.features.authentication.oauth.OauthRedirectGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OauthLoginHandler implements Command.Handler<OauthLoginRequest, OauthLoginResponse> {

    private final OauthRedirectGenerator oauthRedirectGenerator;

    @Autowired
    public OauthLoginHandler(OauthRedirectGenerator oauthRedirectGenerator) {
        this.oauthRedirectGenerator = oauthRedirectGenerator;
    }

    @Override
    public OauthLoginResponse handle(OauthLoginRequest oauthLoginRequest) {
        String redirectUrl = oauthRedirectGenerator.generateUrl(oauthLoginRequest.user());
        return new OauthLoginResponse(redirectUrl);
    }
}
