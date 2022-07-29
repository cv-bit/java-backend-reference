package com.dpi.publishingapi.features.books.book.data.get;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotNull;

public record GetBookDataRequest(@NotNull Long bookId) implements Command<GetBookDataResponse> {
}
