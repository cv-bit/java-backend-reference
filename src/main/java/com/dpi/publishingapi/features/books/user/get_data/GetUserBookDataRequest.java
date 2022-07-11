package com.dpi.publishingapi.features.books.user.get_data;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotNull;

public record GetUserBookDataRequest(@NotNull Long bookId) implements Command<GetUserBookDataResponse> {
}
