package com.dpi.publishingapi.auth;

import com.dpi.publishingapi.auth.dtos.request.LoginRequest;
import com.dpi.publishingapi.auth.dtos.request.RefreshTokenRequest;
import com.dpi.publishingapi.auth.dtos.request.SignupRequest;
import com.dpi.publishingapi.auth.dtos.response.LoginResponse;
import com.dpi.publishingapi.auth.dtos.response.RefreshTokenResponse;
import com.dpi.publishingapi.security.jwt.refresh.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.userLogin(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        authService.userSignup(signupRequest.getEmail(), signupRequest.getPassword());
        return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String token = refreshTokenRequest.getRefreshToken();
        RefreshTokenResponse response = refreshTokenService.refreshToken(token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam Long verificationCode) {
        authService.verifyAccount(verificationCode);
        return ResponseEntity.ok("User successfully verified");
    }

}
