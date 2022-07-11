package com.dpi.publishingapi.features.books.dto;

import com.dpi.publishingapi.data.book.book.Book;
import com.dpi.publishingapi.data.book.language.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Language.class, Collectors.class})
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "publisher", expression = "java(book.getPublisher().getName())")
    @Mapping(target = "languages", expression = "java(book.getLanguages().stream().map(Language::getLanguage).collect(Collectors.toList()))")
    BookDto entityToDTO(Book book);
}
