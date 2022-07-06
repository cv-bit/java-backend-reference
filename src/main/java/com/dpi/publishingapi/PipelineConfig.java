package com.dpi.publishingapi;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.dpi.publishingapi.features.payment.purchase.create.PurchaseCreationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
public class PipelineConfig {

    private final PurchaseCreationHandler purchaseCreationHandler;

    @Autowired
    public PipelineConfig(PurchaseCreationHandler purchaseCreationHandler) {
        this.purchaseCreationHandler = purchaseCreationHandler;
    }

    @Bean
    public Pipeline pipeline() {
        return new Pipelinr()
                .with(() -> Stream.of(purchaseCreationHandler));
    }
}
