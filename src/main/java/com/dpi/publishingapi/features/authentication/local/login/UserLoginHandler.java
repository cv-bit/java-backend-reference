package com.dpi.publishingapi.features.authentication.local.login;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.refresh.RefreshToken;
import com.dpi.publishingapi.data.auth.refresh.RefreshTokenRepository;
import com.dpi.publishingapi.features.authentication.jwt.JwtTokenGenerator;
import com.dpi.publishingapi.features.authentication.refresh.RefreshTokenGenerator;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import com.dpi.publishingapi.infrastructure.security.auth.local.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserLoginHandler implements Command.Handler<UserLoginRequest, UserLoginResponse> {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;


    @Autowired
    public UserLoginHandler(AuthenticationManager authenticationManager,
                            RefreshTokenRepository refreshTokenRepository,
                            JwtTokenGenerator jwtTokenGenerator,
                            RefreshTokenGenerator refreshTokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.refreshTokenGenerator = refreshTokenGenerator;
    }

    @Override
    public UserLoginResponse handle(UserLoginRequest userLoginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.email(), userLoginRequest.password())
            );
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid Username/password", HttpStatus.BAD_REQUEST);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtTokenGenerator.generate(userDetails.getUsername());
        RefreshToken refreshToken = refreshTokenGenerator.generate(userDetails.getUser());
        refreshTokenRepository.save(refreshToken);

        return new UserLoginResponse(token, refreshToken.getToken(), userDetails.getUsername());
    }
}
