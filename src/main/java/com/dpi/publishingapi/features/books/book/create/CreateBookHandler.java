package com.dpi.publishingapi.features.books.book.create;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.creator.CreatorType;
import com.dpi.publishingapi.data.books.difficulty.Difficulty;
import com.dpi.publishingapi.data.books.language.ELanguage;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.pdfinfo.PdfInfo;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.data.books.theme.Theme;
import com.dpi.publishingapi.data.books.type.Type;
import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class CreateBookHandler implements Command.Handler<CreateBookRequest, CreateBookResponse> {

    private final BookRepository bookRepository;

    @Autowired
    public CreateBookHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public CreateBookResponse handle(CreateBookRequest createBookRequest) {
        Book book = bookRepository.findByExternalBookId(createBookRequest.externalBookId()).orElseThrow(() -> new CustomException("Book Does not exist", HttpStatus.NOT_FOUND));
        book.setTitle(createBookRequest.title());
        book.setExternalBookId(createBookRequest.externalBookId());
        book.setCoverUrl(createBookRequest.coverUrl());
        book.setDescription(createBookRequest.description());
        book.setBookDataUrl(createBookRequest.bookDataUrl());
        book.setPublisher(new Publisher(createBookRequest.publisher()));
        book.setSampleVideoUrl(createBookRequest.sampleVideoUrl());
        book.setLanguages(new HashSet<>(createBookRequest.languages().stream().map(language -> new Language(ELanguage.valueOf(language.toUpperCase()))).collect(Collectors.toList())));
        book.setCreators(new HashSet<>(createBookRequest.creators().stream().map(creatorDTO -> new Creator(creatorDTO.name(), CreatorType.valueOf(creatorDTO.creatorType()))).collect(Collectors.toList())));
        book.setType(Type.GUITAR);
        book.setDifficulty(Difficulty.valueOf(createBookRequest.difficulty().toUpperCase()));
        book.setTheme(new Theme(createBookRequest.primaryColor(), createBookRequest.secondaryColor()));
        book.setPdfInfo(new PdfInfo(createBookRequest.thirdPartyPdfUrl(), createBookRequest.dpiPdfUrl(), createBookRequest.pdfCoupon()));
        book.setPrice(new BigDecimal(5.99));

        bookRepository.save(book);

        return new CreateBookResponse("Book successfully created!");
    }
}
