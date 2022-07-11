package com.dpi.publishingapi.features.books.dto;

import com.dpi.publishingapi.books.Difficulty;
import com.dpi.publishingapi.data.book.language.ELanguage;

import java.util.List;

public record BookDto(String title, long externalBookId, String coverUrl, String description, String publisher, String sampleVideoUrl, List<ELanguage> languages, Difficulty difficulty) {
}
