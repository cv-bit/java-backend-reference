package com.dpi.publishingapi;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.dpi.publishingapi.features.books.user.get.GetUserBooksHandler;
import com.dpi.publishingapi.features.books.user.get_data.GetUserBookDataHandler;
import com.dpi.publishingapi.features.payment.purchase.capture.PurchaseCaptureHandler;
import com.dpi.publishingapi.features.payment.purchase.create.PurchaseCreationHandler;
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

    public PipelineConfig(PurchaseCreationHandler purchaseCreationHandler, PurchaseCaptureHandler purchaseCaptureHandler, GetUserBooksHandler getUserBooksHandler, GetUserBookDataHandler getUserBookDataHandler) {
        this.purchaseCreationHandler = purchaseCreationHandler;
        this.purchaseCaptureHandler = purchaseCaptureHandler;
        this.getUserBooksHandler = getUserBooksHandler;
        this.getUserBookDataHandler = getUserBookDataHandler;
    }

    @Autowired


    @Bean
    public Pipeline pipeline() {
        return new Pipelinr()
                .with(() -> Stream.of(purchaseCreationHandler, purchaseCaptureHandler, getUserBooksHandler, getUserBookDataHandler));
    }
}
