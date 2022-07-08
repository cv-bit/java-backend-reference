package com.dpi.publishingapi.features.books.user.get;

import com.dpi.publishingapi.features.books.dto.BookDto;

import java.util.List;

public record GetUserBooksResponse(List<BookDto> books) {
}
