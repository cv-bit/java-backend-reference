package com.dpi.publishingapi.books;

import com.dpi.publishingapi.books.dtos.request.BookCreationRequest;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.creator.CreatorType;
import com.dpi.publishingapi.data.books.language.ELanguage;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.pdfinfo.PdfInfo;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.data.books.theme.Theme;
import com.dpi.publishingapi.data.books.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void createBookFromApi(BookCreationRequest creationRequest) {
        Book book = bookRepository.findByExternalBookId(creationRequest.externalBookId()).get();
        book.setTitle(creationRequest.title());
        book.setExternalBookId(creationRequest.externalBookId());
        book.setCoverUrl(creationRequest.coverUrl());
        book.setDescription(creationRequest.description());
        book.setBookDataUrl(creationRequest.bookDataUrl());
        book.setPublisher(new Publisher(creationRequest.publisher()));
        book.setSampleVideoUrl(creationRequest.sampleVideoUrl());
        book.setLanguages(new HashSet<>(creationRequest.languages().stream().map(language -> new Language(ELanguage.valueOf(language.toUpperCase()))).collect(Collectors.toList())));
        book.setCreators(new HashSet<>(creationRequest.creators().stream().map(creatorDTO -> new Creator(creatorDTO.name(), CreatorType.valueOf(creatorDTO.creatorType()))).collect(Collectors.toList())));
        book.setType(Type.GUITAR);
        book.setDifficulty(Difficulty.valueOf(creationRequest.difficulty().toUpperCase()));
        book.setTheme(new Theme(creationRequest.primaryColor(), creationRequest.secondaryColor()));
        book.setPdfInfo(new PdfInfo(creationRequest.thirdPartyPdfUrl(), creationRequest.dpiPdfUrl(), creationRequest.pdfCoupon()));
        book.setPrice(new BigDecimal(5.99));

        bookRepository.save(book);
    }

}
