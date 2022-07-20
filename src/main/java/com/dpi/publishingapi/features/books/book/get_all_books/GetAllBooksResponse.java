package com.dpi.publishingapi.features.books.book.get_all_books;

import com.dpi.publishingapi.features.books.book.dto.BookDto;

import java.util.List;

public record GetAllBooksResponse(List<BookDto> books) {
}
