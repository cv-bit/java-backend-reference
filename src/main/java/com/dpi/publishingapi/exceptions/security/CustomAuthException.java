package com.dpi.publishingapi.exceptions.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

// This exception class is automatically thrown by spring security when authentication fails
// Shows more details than the default spring security error class
@Component
public class CustomAuthException implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomAuthException(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<String, Object> jsonResponse = Map.of(
                "timestamp", new Date(),
                "code", HttpStatus.UNAUTHORIZED.value(),
                "status", HttpStatus.UNAUTHORIZED.name(),
                "message", exception.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // convert to json
        response.getOutputStream().println(objectMapper.writeValueAsString(jsonResponse));
    }
}
