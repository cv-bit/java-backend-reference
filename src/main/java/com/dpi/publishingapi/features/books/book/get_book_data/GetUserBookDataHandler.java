package com.dpi.publishingapi.features.books.book.get_book_data;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.data.user.User;
import com.dpi.publishingapi.exceptions.CustomException;
import com.dpi.publishingapi.security.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GetUserBookDataHandler implements Command.Handler<GetUserBookDataRequest, GetUserBookDataResponse> {

    private final BookRepository bookRepository;

    @Autowired
    public GetUserBookDataHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public GetUserBookDataResponse handle(GetUserBookDataRequest getUserBookDataRequest) {
        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Book book = bookRepository.findByExternalBookId(getUserBookDataRequest.bookId()).orElseThrow(() -> new CustomException("Book Does not exist", HttpStatus.NOT_FOUND));

        if (!user.getLibrary().contains(book)) {
            throw new CustomException("User does not own book", HttpStatus.UNAUTHORIZED);
        }

        String result = new RestTemplate().exchange(book.getBookDataUrl(), HttpMethod.GET, new HttpEntity<>("", new HttpHeaders()), String.class).getBody();

        return new GetUserBookDataResponse(result);
    }
}
