package com.dpi.publishingapi.auth.dtos.response;

import java.util.List;

public class LoginResponse {
    private String jwt;
    private Long id;
    private String email;
    private List<String> roles;

    public LoginResponse(String jwt, Long id, String email, List<String> roles) {
        this.jwt = jwt;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
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
}