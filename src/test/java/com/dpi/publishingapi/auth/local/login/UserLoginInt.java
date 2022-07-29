package com.dpi.publishingapi.auth.local.login;

import com.dpi.publishingapi.data.auth.refresh.RefreshTokenRepository;
import com.dpi.publishingapi.features.authentication.local.login.UserLoginRequest;
import com.dpi.publishingapi.features.authentication.local.login.UserLoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserLoginInt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String makeRequest(UserLoginRequest request, ResultMatcher status) throws Exception {
        String json = objectMapper.writeValueAsString(request);
        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    public void testUserLoginWorks() throws Exception {
        UserLoginRequest request = new UserLoginRequest("testemail@test.com", "password");

        UserLoginResponse response = objectMapper.readValue(makeRequest(request, status().isOk()), UserLoginResponse.class);

        assertEquals(response.email(), "testemail@test.com");
        assertNotNull(response.accessToken());
        assertNotNull(response.refreshToken());
        assertNotEquals(refreshTokenRepository.findByToken(response.refreshToken()), Optional.empty());
    }

    @Test
    public void testUserLoginWithInvalidCredentials() throws Exception {
        UserLoginRequest request = new UserLoginRequest("testemail@test.com", "password1");

        makeRequest(request, status().isBadRequest());
    }
}
