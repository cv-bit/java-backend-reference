package com.dpi.publishingapi.features.books.book.search;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.features.books.book.dto.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchBooksHandler implements Command.Handler<SearchBooksRequest, SearchBooksResponse> {

    private final BookRepository bookRepository;

    @Autowired
    public SearchBooksHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public SearchBooksResponse handle(SearchBooksRequest searchBooksRequest) {
        List<Book> results;

        if (searchBooksRequest.title().isPresent() && searchBooksRequest.title().get().length() > 0) {
            results = bookRepository.findByTitleContainsIgnoreCase(searchBooksRequest.title().get());
        } else {
            results = bookRepository.findAllFull();
        }

        List<Book> filteredResults = new ArrayList<>(results);

        for (Book book : results) {
            boolean remove = searchBooksRequest.language().isPresent() && !searchBooksRequest.language().get().equals("NA") &&
                    !book.getLanguages().stream()
                            .map(language -> language.getLanguage().toString()).collect(Collectors.toList())
                            .contains(searchBooksRequest.language().get()) ||
                    searchBooksRequest.type().isPresent() &&
                            !book.getType().toString().equals(searchBooksRequest.type().get()) ||
                    searchBooksRequest.publisher().isPresent() &&
                            !book.getPublisher().getName().equals(searchBooksRequest.publisher().get()) ||
                    searchBooksRequest.creator().isPresent() &&
                            !book.getCreators().stream().
                                    map(Creator::getName).collect(Collectors.toList())
                                    .contains(searchBooksRequest.creator().get()) ||
                    (searchBooksRequest.minPrice().isPresent() && searchBooksRequest.maxPrice().isPresent() &&
                            (book.getPrice().doubleValue() < searchBooksRequest.minPrice().get().doubleValue() ||
                                    book.getPrice().doubleValue() > searchBooksRequest.maxPrice().get().doubleValue())) ||
                    searchBooksRequest.difficulty().isPresent() && !book.getDifficulty().toString().equals(searchBooksRequest.difficulty().get().toUpperCase());

            if (remove) {
                filteredResults.remove(book);
            }
        }

        return new SearchBooksResponse(filteredResults.stream()
                .map(book -> BookMapper.INSTANCE.entityToSimpleDTO(book))
                .collect(Collectors.toList()));
    }
}
