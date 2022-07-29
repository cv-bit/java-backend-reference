package com.dpi.publishingapi.features.books.book.get_all;

import com.dpi.publishingapi.features.books.book.dto.SimpleBookDto;

import java.util.List;

public record GetAllBooksResponse(List<SimpleBookDto> books) {
}
