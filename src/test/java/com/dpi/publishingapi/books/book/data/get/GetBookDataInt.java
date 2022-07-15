package com.dpi.publishingapi.books.book.data.get;

import com.dpi.publishingapi.features.books.book.data.get.GetBookDataRequest;
import com.dpi.publishingapi.features.books.book.data.get.GetBookDataResponse;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetBookDataInt {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String makeRequest(GetBookDataRequest request, ResultMatcher status) throws Exception {
        String json = objectMapper.writeValueAsString(request);
        String response = mockMvc.perform(get("/books/data?bookId=" + request.bookId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    @WithUserDetails("testemail@test.com")
    public void testGetBookDataWorking() throws Exception {
        GetBookDataRequest request = new GetBookDataRequest(138L);
        GetBookDataResponse response = objectMapper.readValue(makeRequest(request, status().isOk()), GetBookDataResponse.class);
        assertFalse(response.bookData().isBlank());
    }

    @Test
    @WithUserDetails("testemail20@test.com")
    public void testGetBookDataWhenUserDoesntOwnBook() throws Exception {
        GetBookDataRequest request = new GetBookDataRequest(138L);
        makeRequest(request, status().isUnauthorized());
    }

    @Test
    @WithUserDetails("testemail@test.com")
    public void testGetBookDataWhenBookDoesntExist() throws Exception {
        GetBookDataRequest request = new GetBookDataRequest(100L);
        makeRequest(request, status().isNotFound());
    }
}
