package com.dpi.publishingapi.features.books.book.get;

import an.awesome.pipelinr.Command;

public record GetBookRequest(Long bookId) implements Command<GetBookResponse> {
}
