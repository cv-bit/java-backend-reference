package com.dpi.publishingapi.features.books.reviews.dto;

import com.dpi.publishingapi.data.books.reviews.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ReviewDto entityToDto(Review review);
}