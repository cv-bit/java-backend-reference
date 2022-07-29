package com.dpi.publishingapi.features.books.reviews.get;

import an.awesome.pipelinr.Command;

public record GetReviewsForBookRequest(Long bookId) implements Command<GetReviewsForBookResponse> {
}
