package com.dpi.publishingapi.features.books.book.get_user_books;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.user.User;
import com.dpi.publishingapi.features.books.book.dto.BookDto;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
import com.dpi.publishingapi.security.user.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetUserBooksHandler implements Command.Handler<GetUserBooksRequest, GetUserBooksResponse> {

    @Override
    public GetUserBooksResponse handle(GetUserBooksRequest getUserBooksRequest) {
        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<BookDto> userBooks = user.getLibrary().stream().map(book -> BookMapper.INSTANCE.entityToDTO(book)).toList();
        System.out.println(userBooks);
        return new GetUserBooksResponse(userBooks);
    }
}
