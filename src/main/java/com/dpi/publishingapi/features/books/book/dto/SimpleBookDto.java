package com.dpi.publishingapi.features.books.book.dto;

import java.math.BigDecimal;

public record SimpleBookDto(
        String title,
        String coverUrl,
        Long externalBookId,
        BigDecimal price,
        String creatorNames,
        double rating) {
}

