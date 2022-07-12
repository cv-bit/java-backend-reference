package com.dpi.publishingapi.data.books.book;

import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.data.books.type.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByExternalBookId(Long externalBookId);

    List<Book> findByTitleContainsIgnoreCase(String searchTerm);

    List<Book> findByCreator(Creator creator);

    List<Book> findByLanguage(Language language);

    List<Book> findByPublisher(Publisher publisher);

    List<Book> findByType(Type type);

}
