package com.dpi.publishingapi.features.books.book;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.books.BookService;
import com.dpi.publishingapi.books.dtos.request.BookCreationRequest;
import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.data.books.type.Type;
import com.dpi.publishingapi.features.books.book.get_all_books.GetAllBooksRequest;
import com.dpi.publishingapi.features.books.book.get_all_books.GetAllBooksResponse;
import com.dpi.publishingapi.features.books.book.get_book_data.GetUserBookDataRequest;
import com.dpi.publishingapi.features.books.book.get_book_data.GetUserBookDataResponse;
import com.dpi.publishingapi.features.books.book.get_user_books.GetUserBooksRequest;
import com.dpi.publishingapi.features.books.book.get_user_books.GetUserBooksResponse;
import com.dpi.publishingapi.features.books.book.search_books.SearchBooksRequest;
import com.dpi.publishingapi.features.books.book.search_books.SearchBooksResponse;
import com.dpi.publishingapi.misc.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final Pipeline pipeline;

    @Autowired

    public BookController(BookService bookService, Pipeline pipeline) {
        this.bookService = bookService;
        this.pipeline = pipeline;
    }

    @GetMapping
    public GetAllBooksResponse getBooks() {
        return pipeline.send(new GetAllBooksRequest());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createBook(@RequestBody BookCreationRequest creationRequest) {
        // bookService.createBookFromApi(creationRequest);
        return new ResponseEntity<MessageResponse>(new MessageResponse("Book successfully created!"), HttpStatus.CREATED);
    }

    @GetMapping("/library")
    public GetUserBooksResponse getUserBooks() {
        return pipeline.send(new GetUserBooksRequest());
    }

    @GetMapping("/data")
    public GetUserBookDataResponse getUserBookData(@RequestParam Long bookId) {
        return pipeline.send(new GetUserBookDataRequest(bookId));
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
