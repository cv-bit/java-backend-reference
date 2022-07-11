package com.dpi.publishingapi.features.books.book.get_all_books;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAllBooksHandler implements Command.Handler<GetAllBooksRequest, GetAllBooksResponse> {

    private final BookRepository bookRepository;

    @Autowired
    public GetAllBooksHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public GetAllBooksResponse handle(GetAllBooksRequest getAllBooksRequest) {
        return new GetAllBooksResponse(bookRepository.findAll());
    }
}
