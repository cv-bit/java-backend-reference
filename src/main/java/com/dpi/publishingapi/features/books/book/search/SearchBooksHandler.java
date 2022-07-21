package com.dpi.publishingapi.features.books.book.search;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
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
            results.addAll(searchResults);
            return results.stream().filter(book -> searchResults.contains(book)).collect(Collectors.toList());
        }
        return results;
    }

    @Override
    public SearchBooksResponse handle(SearchBooksRequest searchBooksRequest) {
        List<Book> results = new ArrayList<>();
        results = filterBooks(searchBooksRequest.title(), results, (q) -> bookRepository.findByTitleContainsIgnoreCase((String) q));
        results = filterBooks(searchBooksRequest.creator(), results, (q) -> bookRepository.findByCreator(((Creator) q).getName()));
        results = filterBooks(searchBooksRequest.language(), results, (q) -> bookRepository.findByLanguage(((Language) q).getLanguage().toString()));
        results = filterBooks(searchBooksRequest.publisher(), results, (q) -> bookRepository.findByPublisher(((Publisher) q).getName()));
        results = filterBooks(searchBooksRequest.type(), results, (q) -> bookRepository.findByType(q.toString()));

        return new SearchBooksResponse(results.stream()
                .map(book -> BookMapper.INSTANCE.entityToSimpleDTO(book))
                .collect(Collectors.toList()));
    }
}
