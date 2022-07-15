package com.dpi.publishingapi.books.book.library.get;

import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.features.books.book.dto.BookDto;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
import com.dpi.publishingapi.features.books.book.library.get.GetUserBooksResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetUserBooksInt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetUserBooksInt() throws Exception {
    }

    private String makeRequest(ResultMatcher status) throws Exception {
        String response = mockMvc.perform(get("/books/library"))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    @WithUserDetails("testemail@test.com")
    public void testGetUserBooksWorking() throws Exception {
        GetUserBooksResponse response = objectMapper.readValue(makeRequest(status().isOk()), GetUserBooksResponse.class);
        List<BookDto> book = List.of(BookMapper.INSTANCE.entityToDTO(bookRepository.findById(1L).get()));
        assertEquals(book, response.books());
    }

    @Test
    @WithUserDetails("testemail20@test.com")
    public void testGetUserBooksWhenUserHasNoBooks() throws Exception {
        GetUserBooksResponse response = objectMapper.readValue(makeRequest(status().isOk()), GetUserBooksResponse.class);
        assertTrue(response.books().isEmpty());
    }
}
