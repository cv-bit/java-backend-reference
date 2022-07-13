package com.dpi.publishingapi.features.books.book.library.get;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.features.books.book.dto.BookDto;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
import com.dpi.publishingapi.infrastructure.security.auth.local.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetUserBooksHandler implements Command.Handler<GetUserBooksRequest, GetUserBooksResponse> {

    @Override
    public GetUserBooksResponse handle(GetUserBooksRequest getUserBooksRequest) {
        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<BookDto> userBooks = user.getLibrary().stream().map(book -> BookMapper.INSTANCE.entityToDTO(book)).toList();
        return new GetUserBooksResponse(userBooks);
    }
}
