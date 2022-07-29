package com.dpi.publishingapi.features.books.reviews.dto;

import java.time.Instant;

public record ReviewDto(String displayName, Double rating, String review, Instant creationTime) {
}
