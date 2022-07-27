package com.dpi.publishingapi.features.books.book.search;

import an.awesome.pipelinr.Command;

import java.util.Optional;

public record SearchBooksRequest(Optional<String> title, Optional<String> publisher, Optional<String> language, Optional<String> type, Optional<String> creator, Optional<Integer> minPrice, Optional<Integer> maxPrice) implements Command<SearchBooksResponse> {
}
