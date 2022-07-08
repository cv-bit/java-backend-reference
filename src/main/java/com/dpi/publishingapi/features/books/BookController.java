package com.dpi.publishingapi.features.books;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.books.BookService;
import com.dpi.publishingapi.books.dtos.request.BookCreationRequest;
import com.dpi.publishingapi.features.books.user.get.GetUserBooksRequest;
import com.dpi.publishingapi.features.books.user.get.GetUserBooksResponse;
import com.dpi.publishingapi.misc.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<MessageResponse> getBooks() {
        return ResponseEntity.ok(new MessageResponse("Test"));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createBook(@RequestBody BookCreationRequest creationRequest) {
        bookService.createBookFromApi(creationRequest);
        return new ResponseEntity<MessageResponse>(new MessageResponse("Book successfully created!"), HttpStatus.CREATED);
    }

    @GetMapping("/library")
    public GetUserBooksResponse getUserBooks() {
        return pipeline.send(new GetUserBooksRequest());
    }
}
