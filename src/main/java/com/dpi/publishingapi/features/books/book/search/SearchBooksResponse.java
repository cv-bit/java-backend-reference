package com.dpi.publishingapi.features.books.book.search;

import com.dpi.publishingapi.features.books.book.dto.BookDto;

import java.util.List;

public record SearchBooksResponse(List<BookDto> books) {
}
