package com.dpi.publishingapi.books;

import com.dpi.publishingapi.books.book.Book;
import com.dpi.publishingapi.books.book.BookRepository;
import com.dpi.publishingapi.books.dtos.request.BookCreationRequest;
import com.dpi.publishingapi.books.language.ELanguage;
import com.dpi.publishingapi.books.language.Language;
import com.dpi.publishingapi.books.pdfinfo.PdfInfo;
import com.dpi.publishingapi.books.publisher.Publisher;
import com.dpi.publishingapi.books.theme.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void createBookFromApi(BookCreationRequest creationRequest) {
        Book book = new Book(
                creationRequest.getTitle(),
                (long) creationRequest.getExternalBookId(),
                creationRequest.getCoverUrl(),
                creationRequest.getDescription(),
                creationRequest.getBookDataUrl(),
                new Publisher(creationRequest.getPublisher()),
                creationRequest.getSampleVideoUrl(),
                new HashSet<>(creationRequest.getLanguages().stream().map(language -> new Language(ELanguage.valueOf(language.toUpperCase()))).collect(Collectors.toList())),
                Difficulty.valueOf(creationRequest.getDifficulty().toUpperCase()),
                new Theme(creationRequest.getPrimaryColor(), creationRequest.getSecondaryColor()),
                new PdfInfo(creationRequest.getThirdPartyPdfUrl(), creationRequest.getDpiPdfUrl(), creationRequest.getPdfCoupon())
        );
        bookRepository.save(book);
    }

}
