package com.dpi.publishingapi.features.authentication.oauth;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.auth.user.UserRepository;
import com.dpi.publishingapi.features.authentication.oauth.login.OauthLoginRequest;
import com.dpi.publishingapi.features.authentication.oauth.signup.OauthSignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OauthHandler {

    private final UserRepository userRepository;
    private final Pipeline pipeline;

    @Autowired
    public OauthHandler(UserRepository userRepository, Pipeline pipeline) {
        this.userRepository = userRepository;
        this.pipeline = pipeline;
    }

    // Handles where the oauth login request will be forwarded.  Either Signup or login
    public String process(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        String url;

        if (user.isPresent()) {
            url = pipeline.send(new OauthLoginRequest(user.get())).redirectUrl();
        } else {
            url = pipeline.send(new OauthSignupRequest(email)).redirectUrl();
        }

        return url;
    }
}
