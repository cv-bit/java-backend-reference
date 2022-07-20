package com.dpi.publishingapi.features.books.book.search_books;

import com.dpi.publishingapi.features.books.book.dto.BookDto;

import java.util.List;

public record SearchBooksResponse(List<BookDto> books) {
}
