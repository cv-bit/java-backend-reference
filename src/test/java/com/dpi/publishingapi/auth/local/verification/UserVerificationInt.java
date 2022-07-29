package com.dpi.publishingapi.auth.local.verification;

import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.auth.user.UserRepository;
import com.dpi.publishingapi.features.authentication.local.verification.UserVerificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserVerificationInt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String makeRequest(UserVerificationRequest request, ResultMatcher status) throws Exception {
        String json = objectMapper.writeValueAsString(request);
        String response = mockMvc.perform(get("/auth/verify?verificationCode=" + request.verificationCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    public void testUserVerificationWorks() throws Exception {
        UserVerificationRequest request = new UserVerificationRequest(123456L);
        makeRequest(request, status().isOk());
        User user = userRepository.findByEmail("testemail10@test.com").get();

        assertNull(user.getVerificationCode());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testUserVerificationWithInvalidCode() throws Exception {
        UserVerificationRequest request = new UserVerificationRequest(333333L);
        makeRequest(request, status().isNotFound());
    }

    @Test
    public void testUserVerificationWithAlreadyVerified() throws Exception {
        UserVerificationRequest request = new UserVerificationRequest(444444L);
        makeRequest(request, status().isConflict());
    }

}