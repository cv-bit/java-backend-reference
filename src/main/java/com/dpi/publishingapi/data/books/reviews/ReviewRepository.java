package com.dpi.publishingapi.data.books.reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.user.id = ?1 AND r.book.externalBookId = ?2")
    Optional<Review> findByUserAndBook(Long userId, Long bookId);

    @Query("SELECT r FROM Review r WHERE r.book.externalBookId = ?1")
    List<Review> findAllByBook(Long bookId);
}
