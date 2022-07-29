package com.dpi.publishingapi.features.books.book;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.data.books.book.BookRepository;
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
import com.dpi.publishingapi.features.books.reviews.create.CreateReviewRequest;
import com.dpi.publishingapi.features.books.reviews.create.CreateReviewResponse;
import com.dpi.publishingapi.features.books.reviews.get.GetReviewsForBookRequest;
import com.dpi.publishingapi.features.books.reviews.get.GetReviewsForBookResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private final Pipeline pipeline;

    public BookController(Pipeline pipeline) 
    @Autowired
    public BookController(Pipeline pipeline, BookRepository bookRepository) {
        this.pipeline = pipeline;
    }

    @GetMapping("/all")
    public GetAllBooksResponse getBooks() {
        return pipeline.send(new GetAllBooksRequest());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateBookResponse createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
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
                                           @RequestParam Optional<String> publisher,
                                           @RequestParam Optional<String> language,
                                           @RequestParam Optional<String> type,
                                           @RequestParam Optional<String> creator,
                                           @RequestParam Optional<Integer> minPrice,
                                           @RequestParam Optional<Integer> maxPrice) {
        return pipeline.send(new SearchBooksRequest(
                title,
                publisher,
                language,
                type,
                creator,
                minPrice,
                maxPrice
        ));
    }

    @PostMapping("/reviews")
    public CreateReviewResponse createBookReview(@Valid @RequestBody CreateReviewRequest createReviewRequest) {
        return pipeline.send(createReviewRequest);
    }

    @GetMapping("/reviews")
    public GetReviewsForBookResponse getReviewsForBook(@RequestParam Long bookId) {
        return pipeline.send(new GetReviewsForBookRequest(bookId));
    }
}
