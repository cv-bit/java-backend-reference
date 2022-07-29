package com.dpi.publishingapi.features.books.reviews.get;

import com.dpi.publishingapi.features.books.reviews.dto.ReviewDto;

import java.util.List;

public record GetReviewsForBookResponse(List<ReviewDto> reviews) {
}
