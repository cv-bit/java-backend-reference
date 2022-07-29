package com.dpi.publishingapi.features.books.book.search;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.data.books.type.Type;

import java.util.Optional;

public record SearchBooksRequest(Optional<String> title, Optional<Publisher> publisher, Optional<Language> language, Optional<Type> type, Optional<Creator> creator) implements Command<SearchBooksResponse> {
}
