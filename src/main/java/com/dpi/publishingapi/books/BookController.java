package com.dpi.publishingapi.books;

import com.dpi.publishingapi.books.dtos.request.BookCreationRequest;
import com.dpi.publishingapi.misc.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getBooks() {
        return ResponseEntity.ok(new MessageResponse("Test"));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createBook(@RequestBody BookCreationRequest creationRequest) {
        bookService.createBookFromApi(creationRequest);
        return ResponseEntity.ok(new MessageResponse("Book successfully created!"));
    }
}
