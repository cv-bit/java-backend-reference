package com.dpi.publishingapi.books;

import com.dpi.publishingapi.misc.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public ResponseEntity<MessageResponse> getBooks() {
        return ResponseEntity.ok(new MessageResponse("Test"));
    }
}