package com.dpi.publishingapi.features.books.dto;

import com.dpi.publishingapi.books.Difficulty;
import com.dpi.publishingapi.data.book.language.Language;
import com.dpi.publishingapi.data.book.publisher.Publisher;

import java.util.Set;

public record BookDto(String title, long externalBookId, String coverUrl, String description, Publisher publisher, String sampleVideoUrl, Set<Language> languages, Difficulty difficulty) {
}
