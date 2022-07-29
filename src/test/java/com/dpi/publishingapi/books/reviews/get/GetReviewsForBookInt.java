package com.dpi.publishingapi.books.reviews.get;

import com.dpi.publishingapi.features.books.reviews.get.GetReviewsForBookRequest;
import com.dpi.publishingapi.features.books.reviews.get.GetReviewsForBookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetReviewsForBookInt {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String makeRequest(GetReviewsForBookRequest request, ResultMatcher status) throws Exception {
        String json = objectMapper.writeValueAsString(request);
        String response = mockMvc.perform(get("/books/reviews/?bookId=" + request.bookId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    public void testGetReviewsForBookWorking() throws Exception {
        GetReviewsForBookRequest request = new GetReviewsForBookRequest(138L);
        GetReviewsForBookResponse response = objectMapper.readValue(makeRequest(request, status().isOk()), GetReviewsForBookResponse.class);
        assertTrue(response.reviews().size() > 0);
    }

    @Test
    public void testGetReviewsForBookWhenBookDoesntExist() throws Exception {
        GetReviewsForBookRequest request = new GetReviewsForBookRequest(100L);
        makeRequest(request, status().isNotFound());
    }
}
