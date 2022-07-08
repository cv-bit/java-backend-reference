package com.dpi.publishingapi.features.books.user.get;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.user.User;
import com.dpi.publishingapi.features.books.dto.BookDto;
import com.dpi.publishingapi.features.books.dto.BookMapper;
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
        return new GetUserBooksResponse(userBooks);
    }
}
