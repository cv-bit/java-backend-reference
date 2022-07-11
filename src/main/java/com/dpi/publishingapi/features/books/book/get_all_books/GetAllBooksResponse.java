package com.dpi.publishingapi.features.books.book.get_all_books;

import com.dpi.publishingapi.data.books.book.Book;

import java.util.List;

public record GetAllBooksResponse(List<Book> books) {
}
