package com.dpi.publishingapi.features.books.book.get_book_data;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotNull;

public record GetUserBookDataRequest(@NotNull Long bookId) implements Command<GetUserBookDataResponse> {
}
