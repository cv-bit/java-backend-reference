package com.dpi.publishingapi.features.books.book.search;

import com.dpi.publishingapi.features.books.book.dto.SimpleBookDto;

import java.util.List;

public record SearchBooksResponse(List<SimpleBookDto> books) {
}
