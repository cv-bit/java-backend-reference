package com.dpi.publishingapi.features.books.book.get_all;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GetAllBooksHandler implements Command.Handler<GetAllBooksRequest, GetAllBooksResponse> {

    private final BookRepository bookRepository;

    @Autowired
    public GetAllBooksHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public GetAllBooksResponse handle(GetAllBooksRequest getAllBooksRequest) {
        return new GetAllBooksResponse(bookRepository.findAllMinimum().stream().
                map(BookMapper.INSTANCE::entityToSimpleDTO)
                .collect(Collectors.toList()));
    }

}
