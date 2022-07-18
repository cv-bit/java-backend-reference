package com.dpi.publishingapi.books.reviews.create;

import com.dpi.publishingapi.data.books.reviews.ReviewRepository;
import com.dpi.publishingapi.features.books.reviews.create.CreateReviewRequest;
import com.dpi.publishingapi.features.books.reviews.create.CreateReviewResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreateReviewInt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private String makeRequest(CreateReviewRequest request, ResultMatcher status) throws Exception {
        String json = objectMapper.writeValueAsString(request);
        String response = mockMvc.perform(post("/books/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    @WithUserDetails("testemail@test.com")
    public void testCreateReviewWorking() throws Exception {
        CreateReviewRequest request = new CreateReviewRequest("test user", 3.8, "Test review", 138L);
        CreateReviewResponse response = objectMapper.readValue(makeRequest(request, status().isOk()), CreateReviewResponse.class);
        assertTrue(reviewRepository.existsById(1L));
        assertEquals(reviewRepository.findById(1L).get().getBook().getId(), 1L);
    }

    @Test
    @WithUserDetails("testemail@test.com")
    public void testCreateReviewWhenBookDoesntExist() throws Exception {
        CreateReviewRequest request = new CreateReviewRequest("test user", 3.8, "Test review", 100L);
        makeRequest(request, status().isNotFound());
    }

    @Test
    @WithUserDetails("testemail20@test.com")
    public void testCreateReviewWhenUserDoesntOwnBook() throws Exception {
        CreateReviewRequest request = new CreateReviewRequest("test user", 3.8, "Test review", 138L);
        makeRequest(request, status().isUnauthorized());
    }

    @Test
    @WithUserDetails("testemail@test.com")
    public void testCreateReviewWhenUserAlreadyLeftReview() throws Exception {
        CreateReviewRequest request = new CreateReviewRequest("test user", 3.8, "Test review", 138L);
        makeRequest(request, status().isConflict());
        makeRequest(request, status().isConflict());
        assertFalse(reviewRepository.findAll().size() > 2);
    }
}
