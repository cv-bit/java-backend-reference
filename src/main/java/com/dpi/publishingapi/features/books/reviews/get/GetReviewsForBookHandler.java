package com.dpi.publishingapi.features.books.reviews.get;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.data.books.reviews.ReviewRepository;
import com.dpi.publishingapi.features.books.reviews.dto.ReviewMapper;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GetReviewsForBookHandler implements Command.Handler<GetReviewsForBookRequest, GetReviewsForBookResponse> {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public GetReviewsForBookHandler(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public GetReviewsForBookResponse handle(GetReviewsForBookRequest getReviewsForBookRequest) {
        Book book = bookRepository.findByExternalBookId(getReviewsForBookRequest.bookId()).orElseThrow(() -> new CustomException("Book does not exist", HttpStatus.NOT_FOUND));
        return new GetReviewsForBookResponse(
                book.getReviews().stream()
                        .map(ReviewMapper.INSTANCE::entityToDto)
                        .collect(Collectors.toList()));
    }
}
