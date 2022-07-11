package com.dpi.publishingapi.features.books.book.search_books;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.data.books.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchBooksHandler implements Command.Handler<SearchBooksRequest, SearchBooksResponse> {

    private final BookRepository bookRepository;

    @Autowired
    public SearchBooksHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private interface SearchFunction<T> {
        List<Book> search(T keyword);
    }

    private <T> List<Book> filterBooks(Optional<T> query, List<Book> results, SearchFunction searchFunction) {
        if (query.isPresent()) {
            T searchTerm = query.get();
            List<Book> searchResults = searchFunction.search(searchTerm);
            return results.stream().filter(book -> searchResults.contains(book)).collect(Collectors.toList());
        }
        return results;
    }

    @Override
    public SearchBooksResponse handle(SearchBooksRequest searchBooksRequest) {
        List<Book> results = new ArrayList<>();
        results = filterBooks(searchBooksRequest.title(), results, (q) -> bookRepository.findByTitleContainsIgnoreCase((String) q));
        results = filterBooks(searchBooksRequest.creator(), results, (q) -> bookRepository.findByCreator((Creator) q));
        results = filterBooks(searchBooksRequest.language(), results, (q) -> bookRepository.findByLanguage((Language) q));
        results = filterBooks(searchBooksRequest.publisher(), results, (q) -> bookRepository.findByPublisher((Publisher) q));
        results = filterBooks(searchBooksRequest.type(), results, (q) -> bookRepository.findByType((Type) q));

        return new SearchBooksResponse(results);
    }
}
