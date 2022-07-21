package com.dpi.publishingapi.data.books.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByExternalBookId(Long externalBookId);

    List<Book> findByTitleContainsIgnoreCase(String searchTerm);

    @Query("SELECT b FROM Book b JOIN Creator c WHERE c.name = ?1")
    List<Book> findByCreator(String creator);

    @Query("SELECT b FROM Book b JOIN Language l WHERE l.language = ?1")
    List<Book> findByLanguage(String language);

    @Query("SELECT b FROM Book b WHERE b.publisher.name = ?1")
    List<Book> findByPublisher(String publisher);

    @Query("SELECT b FROM Book b WHERE b.type = ?1")
    List<Book> findByType(String type);
}
