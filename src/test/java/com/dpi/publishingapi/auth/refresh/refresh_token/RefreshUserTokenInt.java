package com.dpi.publishingapi.auth.refresh.refresh_token;

import com.dpi.publishingapi.features.authentication.refresh.refresh_token.RefreshUserTokenRequest;
import com.dpi.publishingapi.features.authentication.refresh.refresh_token.RefreshUserTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RefreshUserTokenInt {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String makeRequest(RefreshUserTokenRequest request, ResultMatcher status) throws Exception {
        String json = objectMapper.writeValueAsString(request);
        String response = mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    public void testRefreshUserTokenWorks() throws Exception {
        RefreshUserTokenRequest request = new RefreshUserTokenRequest("testToken");
        RefreshUserTokenResponse response = objectMapper.readValue(
                makeRequest(request, status().isOk()), RefreshUserTokenResponse.class
        );

        assertEquals("testToken", response.refreshToken());
        assertNotNull(response.accessToken());
    }

    @Test
    public void testRefreshUserTokenWithInvalidToken() throws Exception {
        RefreshUserTokenRequest request = new RefreshUserTokenRequest("invalidToken");
        makeRequest(request, status().isNotFound());
    }

    @Test
    public void testRefreshUserTokenWithExpiredToken() throws Exception {
        RefreshUserTokenRequest request = new RefreshUserTokenRequest("expiredToken");
        makeRequest(request, status().isBadRequest());
    }

}
