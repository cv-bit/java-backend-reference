package com.dpi.publishingapi.features.books.book.search_books;

import com.dpi.publishingapi.data.books.book.Book;

import java.util.List;

public record SearchBooksResponse(List<Book> books) {
}
