package com.dpi.publishingapi.books.book.get;


import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.features.books.book.dto.BookDto;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
import com.dpi.publishingapi.features.books.book.get.GetBookRequest;
import com.dpi.publishingapi.features.books.book.get.GetBookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetBookInt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String makeRequest(GetBookRequest request, ResultMatcher status) throws Exception {
        String json = objectMapper.writeValueAsString(request);
        String response = mockMvc.perform(get("/books/?bookId=" + request.bookId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status)
                .andReturn().getResponse().getContentAsString();
        return response;
    }

    @Test
    public void testGetBooksWorking() throws Exception {
        GetBookRequest request = new GetBookRequest(138L);
        GetBookResponse response = objectMapper.readValue(makeRequest(request, status().isOk()), GetBookResponse.class);

        Book book = bookRepository.findByExternalBookId(138L).get();
        BookDto bookDto = BookMapper.INSTANCE.entityToDTO(book);

        Assertions.assertEquals(bookDto, response.book());
    }

    @Test
    public void testGetBookWhenBookDoesntExist() throws Exception {
        GetBookRequest request = new GetBookRequest(100L);
        makeRequest(request, status().isNotFound());
    }
}
