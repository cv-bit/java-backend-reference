package com.dpi.publishingapi.features.books.book.dto;

import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.features.books.book.create.CreatorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Language.class, Collectors.class, CreatorDTO.class})
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "publisher", expression = "java(book.getPublisher().getName())")
    @Mapping(target = "languages", expression = "java(book.getLanguages().stream().map(Language::getLanguage).collect(Collectors.toList()))")
    @Mapping(target = "creators", expression = "java(book.getCreators().stream().map(creator -> new Creator(creator.getName(), creator.getCreatorType())).collect(Collectors.toList()))")
    @Mapping(target = "rating", expression = "java(book.getRating().getAverage())")
    BookDto entityToDTO(Book book);

    @Mapping(target = "creatorNames", expression = "java(String.join(\", \",book.getCreators().stream().map(creator -> creator.getName()).distinct().collect(Collectors.toList())))")
    @Mapping(target = "rating", expression = "java( book.getRating().getAverage())")
    SimpleBookDto entityToSimpleDTO(Book book);
}
