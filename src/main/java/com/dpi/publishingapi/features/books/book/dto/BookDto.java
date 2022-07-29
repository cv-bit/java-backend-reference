package com.dpi.publishingapi.features.books.book.dto;

import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.difficulty.Difficulty;
import com.dpi.publishingapi.data.books.language.ELanguage;
import com.dpi.publishingapi.data.books.type.Type;

import java.math.BigDecimal;
import java.util.List;

public record BookDto(String title,
                      long externalBookId,
                      String coverUrl,
                      String description,
                      String publisher,
                      String sampleVideoUrl,
                      List<ELanguage> languages,
                      Difficulty difficulty,
                      Type type,
                      BigDecimal price,
                      List<Creator> creators,
                      Double rating) {
}
