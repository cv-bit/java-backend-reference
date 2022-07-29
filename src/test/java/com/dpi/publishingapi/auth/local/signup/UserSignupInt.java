package com.dpi.publishingapi.auth.local.signup;

import com.dpi.publishingapi.data.auth.user.LoginMethod;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.auth.user.UserRepository;
import com.dpi.publishingapi.features.authentication.local.signup.UserSignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserSignupInt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String makeRequest(UserSignupRequest request, ResultMatcher status) throws Exception {
        String json = objectMapper.writeValueAsString(request);
        String response = mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    public void testUserSignupWorks() throws Exception {
        String email = "testemail2@test.com";
        UserSignupRequest request = new UserSignupRequest(email, "password");
        makeRequest(request, status().isCreated());
        assertTrue(userRepository.existsByEmail(email));
        User user = userRepository.findByEmail(email).get();
        assertEquals(user.getEmail(), email);
        assertEquals(user.getLoginMethod(), LoginMethod.LOCAL);
        assertNotNull(user.getVerificationCode());
    }

    @Test
    public void testUserSignupWithTakenEmail() throws Exception {
        String email = "testemail@test.com";
        UserSignupRequest request = new UserSignupRequest(email, "password");

        makeRequest(request, status().isConflict());
    }

}
