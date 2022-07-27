package com.dpi.publishingapi.data.books.rating;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findTopByOrderByIdDesc();
}
