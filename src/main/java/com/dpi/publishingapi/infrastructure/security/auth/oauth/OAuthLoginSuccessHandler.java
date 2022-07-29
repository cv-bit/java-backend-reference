package com.dpi.publishingapi.infrastructure.security.auth.oauth;


import com.dpi.publishingapi.features.authentication.oauth.OauthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final OauthHandler oauthHandler;

    @Autowired
    public OAuthLoginSuccessHandler(OauthHandler oauthHandler) {
        this.oauthHandler = oauthHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomOauthUser oauthUser = (CustomOauthUser) authentication.getPrincipal();
        response.sendRedirect(oauthHandler.process(oauthUser.getEmail()));
    }
}
