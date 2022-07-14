package com.dpi.publishingapi.features.books.book.get;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public
class GetBookHandler implements Command.Handler<GetBookRequest, GetBookResponse> {
    private final BookRepository bookRepository;

    @Autowired
    public GetBookHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public GetBookResponse handle(GetBookRequest getBookRequest) {
        Book book = bookRepository.findByExternalBookId(getBookRequest.bookId()).orElseThrow(() -> new CustomException("Book does not exist", HttpStatus.NOT_FOUND));
        return new GetBookResponse(BookMapper.INSTANCE.entityToDTO(book));
    }
}
