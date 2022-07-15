package com.dpi.publishingapi.books.book.get_all;

import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.features.books.book.dto.BookDto;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
import com.dpi.publishingapi.features.books.book.get_all.GetAllBooksResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetAllBooksInt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String makeRequest(ResultMatcher status) throws Exception {
        String response = mockMvc.perform(get("/books/all"))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    public void testGetAllBooksWorking() throws Exception {
        GetAllBooksResponse response = objectMapper.readValue(makeRequest(status().isOk()), GetAllBooksResponse.class);
        List<BookDto> books = List.of(BookMapper.INSTANCE.entityToDTO(bookRepository.findById(1L).get()));
        assertEquals(books, response.books());
    }
}