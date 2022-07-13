package com.dpi.publishingapi.features.books.book.data.get;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import com.dpi.publishingapi.infrastructure.security.auth.local.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GetBookDataHandler implements Command.Handler<GetBookDataRequest, GetBookDataResponse> {

    private final BookRepository bookRepository;

    @Autowired
    public GetBookDataHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public GetBookDataResponse handle(GetBookDataRequest getUserBookDataRequest) {
        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Book book = bookRepository.findByExternalBookId(getUserBookDataRequest.bookId()).orElseThrow(() -> new CustomException("Book Does not exist", HttpStatus.NOT_FOUND));

        if (!user.getLibrary().contains(book)) {
            throw new CustomException("User does not own book", HttpStatus.UNAUTHORIZED);
        }

        String result = new RestTemplate().exchange(book.getBookDataUrl(), HttpMethod.GET, new HttpEntity<>("", new HttpHeaders()), String.class).getBody();

        return new GetBookDataResponse(result);
    }
}
