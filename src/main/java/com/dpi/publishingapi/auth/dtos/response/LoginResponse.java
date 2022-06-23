package com.dpi.publishingapi.auth.dtos.response;

import java.util.List;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long id;
    private String email;
    private List<String> roles;

    public LoginResponse(String accessToken, String refreshToken, Long id, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.refreshToken = refreshToken;
    }

    public String getJwt() {
        return accessToken;
    }

    public void setJwt(String jwt) {
        this.accessToken = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}