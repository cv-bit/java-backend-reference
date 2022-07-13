package com.dpi.publishingapi.features.authentication;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.features.authentication.local.login.UserLoginRequest;
import com.dpi.publishingapi.features.authentication.local.login.UserLoginResponse;
import com.dpi.publishingapi.features.authentication.local.signup.UserSignupRequest;
import com.dpi.publishingapi.features.authentication.local.signup.UserSignupResponse;
import com.dpi.publishingapi.features.authentication.local.verification.UserVerificationRequest;
import com.dpi.publishingapi.features.authentication.local.verification.UserVerificationResponse;
import com.dpi.publishingapi.features.authentication.refresh.refresh_token.RefreshUserTokenRequest;
import com.dpi.publishingapi.features.authentication.refresh.refresh_token.RefreshUserTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Pipeline pipeline;

    @Autowired
    public AuthController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping(value = "/login")
    public UserLoginResponse userSignup(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return pipeline.send(userLoginRequest);
    }

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserSignupResponse userLogin(@Valid @RequestBody UserSignupRequest userSignupRequest) {
        return pipeline.send(userSignupRequest);
    }

    @PostMapping("/refresh")
    public RefreshUserTokenResponse refreshToken(@Valid @RequestBody RefreshUserTokenRequest refreshUserTokenRequest) {
        return pipeline.send(refreshUserTokenRequest);
    }

    @GetMapping("/verify")
    public UserVerificationResponse verifyEmail(@RequestParam Long verificationCode) {
        return pipeline.send(new UserVerificationRequest(verificationCode));
    }

}
