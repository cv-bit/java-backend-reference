package com.dpi.publishingapi.features.books.book.library.get;

import com.dpi.publishingapi.features.books.book.dto.BookDto;

import java.util.List;

public record GetUserBooksResponse(List<BookDto> books) {
}
