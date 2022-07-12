package com.dpi.publishingapi.features.books.language.get_all_languages;

import com.dpi.publishingapi.data.books.language.Language;

import java.util.List;

public record GetAllLanguagesResponse(List<Language> languages) {
}
