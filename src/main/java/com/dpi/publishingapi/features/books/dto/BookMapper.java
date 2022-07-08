package com.dpi.publishingapi.features.books.dto;

import com.dpi.publishingapi.data.book.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto entityToDTO(Book book);
}
