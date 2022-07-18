package com.dpi.publishingapi.features.books.reviews.create;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateReviewRequest(
        @NotBlank
        String displayName,
        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "5.0")
        Double rating,
        @NotBlank
        String review,
        @NotNull
        Long bookId) implements Command<CreateReviewResponse> {
}
