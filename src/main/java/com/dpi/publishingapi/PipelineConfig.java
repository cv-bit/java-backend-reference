package com.dpi.publishingapi;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.dpi.publishingapi.features.books.book.get_all_books.GetAllBooksHandler;
import com.dpi.publishingapi.features.books.book.get_book_data.GetUserBookDataHandler;
import com.dpi.publishingapi.features.books.book.get_user_books.GetUserBooksHandler;
import com.dpi.publishingapi.features.books.book.search_books.SearchBooksHandler;
import com.dpi.publishingapi.features.books.creator.get_all_creators.GetAllCreatorsHandler;
import com.dpi.publishingapi.features.books.language.get_all_languages.GetAllLanguagesHandler;
import com.dpi.publishingapi.features.books.publisher.get_all_publishers.GetAllPublishersHandler;
import com.dpi.publishingapi.features.books.type.get_all_types.GetAllTypesHandler;
import com.dpi.publishingapi.features.payment.purchase.capture_purchase.PurchaseCaptureHandler;
import com.dpi.publishingapi.features.payment.purchase.create_purchase.PurchaseCreationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
public class PipelineConfig {

    private final PurchaseCreationHandler purchaseCreationHandler;
    private final PurchaseCaptureHandler purchaseCaptureHandler;
    private final GetUserBooksHandler getUserBooksHandler;
    private final GetUserBookDataHandler getUserBookDataHandler;
    private final GetAllLanguagesHandler getAllLanguagesHandler;
    private final GetAllTypesHandler getAllTypesHandler;
    private final GetAllPublishersHandler getAllPublishersHandler;
    private final GetAllCreatorsHandler getAllCreatorsHandler;
    private final GetAllBooksHandler getAllBooksHandler;
    private final SearchBooksHandler searchBooksHandler;


    @Autowired
    public PipelineConfig(PurchaseCreationHandler purchaseCreationHandler, PurchaseCaptureHandler purchaseCaptureHandler, GetUserBooksHandler getUserBooksHandler, GetUserBookDataHandler getUserBookDataHandler, GetAllLanguagesHandler getAllLanguagesHandler, GetAllTypesHandler getAllTypesHandler, GetAllPublishersHandler getAllPublishersHandler, GetAllCreatorsHandler getAllCreatorsHandler, GetAllBooksHandler getAllBooksHandler, SearchBooksHandler searchBooksHandler) {
        this.purchaseCreationHandler = purchaseCreationHandler;
        this.purchaseCaptureHandler = purchaseCaptureHandler;
        this.getUserBooksHandler = getUserBooksHandler;
        this.getUserBookDataHandler = getUserBookDataHandler;
        this.getAllLanguagesHandler = getAllLanguagesHandler;
        this.getAllTypesHandler = getAllTypesHandler;
        this.getAllPublishersHandler = getAllPublishersHandler;
        this.getAllCreatorsHandler = getAllCreatorsHandler;
        this.getAllBooksHandler = getAllBooksHandler;
        this.searchBooksHandler = searchBooksHandler;
    }

    @Bean
    public Pipeline pipeline() {
        return new Pipelinr()
                .with(() -> Stream.of(purchaseCreationHandler,
                        purchaseCaptureHandler,
                        getUserBooksHandler,
                        getUserBookDataHandler,
                        getAllLanguagesHandler,
                        getAllTypesHandler,
                        getAllPublishersHandler,
                        getAllCreatorsHandler,
                        getAllBooksHandler,
                        searchBooksHandler));
    }
}
