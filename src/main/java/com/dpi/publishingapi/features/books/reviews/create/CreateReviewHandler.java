package com.dpi.publishingapi.features.books.reviews.create;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.data.books.reviews.Review;
import com.dpi.publishingapi.data.books.reviews.ReviewRepository;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import com.dpi.publishingapi.infrastructure.security.auth.local.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class CreateReviewHandler implements Command.Handler<CreateReviewRequest, CreateReviewResponse> {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public CreateReviewHandler(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public CreateReviewResponse handle(CreateReviewRequest createReviewRequest) {
        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Book book = bookRepository.findByExternalBookId(createReviewRequest.bookId()).orElseThrow(() -> new CustomException("Book doesn't exist", HttpStatus.NOT_FOUND));

        if (!user.getLibrary().contains(book)) {
            throw new CustomException("User does not own book", HttpStatus.UNAUTHORIZED);
        }

        if (reviewRepository.findByUserAndBook(user.getId(), book.getExternalBookId()).isPresent()) {
            throw new CustomException("User already left a review", HttpStatus.CONFLICT);
        }

        Review review = new Review(createReviewRequest.displayName(), user, book, createReviewRequest.rating(), createReviewRequest.review());
        reviewRepository.save(review);

        return new CreateReviewResponse("Review successfully created");
    }
}
