package com.dpi.publishingapi.features.books.book;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.data.books.type.Type;
import com.dpi.publishingapi.features.books.book.create.CreateBookRequest;
import com.dpi.publishingapi.features.books.book.create.CreateBookResponse;
import com.dpi.publishingapi.features.books.book.data.get.GetBookDataRequest;
import com.dpi.publishingapi.features.books.book.data.get.GetBookDataResponse;
import com.dpi.publishingapi.features.books.book.get.GetBookRequest;
import com.dpi.publishingapi.features.books.book.get.GetBookResponse;
import com.dpi.publishingapi.features.books.book.get_all.GetAllBooksRequest;
import com.dpi.publishingapi.features.books.book.get_all.GetAllBooksResponse;
import com.dpi.publishingapi.features.books.book.library.get.GetUserBooksRequest;
import com.dpi.publishingapi.features.books.book.library.get.GetUserBooksResponse;
import com.dpi.publishingapi.features.books.book.search.SearchBooksRequest;
import com.dpi.publishingapi.features.books.book.search.SearchBooksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final Pipeline pipeline;

    @Autowired

    public BookController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/all")
    public GetAllBooksResponse getBooks() {
        return pipeline.send(new GetAllBooksRequest());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateBookResponse createBook(@RequestBody CreateBookRequest createBookRequest) {
        return pipeline.send(createBookRequest);
    }

    @GetMapping
    public GetBookResponse getBook(@RequestParam Long bookId) {
        return pipeline.send(new GetBookRequest(bookId));
    }

    @GetMapping("/library")
    public GetUserBooksResponse getUserBooks() {
        return pipeline.send(new GetUserBooksRequest());
    }

    @GetMapping(value = "/data")
    public GetBookDataResponse getUserBookData(@RequestParam Long bookId) {
        return pipeline.send(new GetBookDataRequest(bookId));
    }

    @GetMapping("/search")
    public SearchBooksResponse searchBooks(@RequestParam Optional<String> title,
                                           @RequestParam Optional<Publisher> publisher,
                                           @RequestParam Optional<Language> language,
                                           @RequestParam Optional<Type> type,
                                           @RequestParam Optional<Creator> creator) {
        return pipeline.send(new SearchBooksRequest(
                title,
                publisher,
                language,
                type,
                creator
        ));
    }
}
