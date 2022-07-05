package com.dpi.publishingapi.payment.purchase;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.payment.purchase.create.PurchaseCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PurchaseController {

    private final Pipeline pipeline;

    @Autowired
    public PurchaseController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping
    public void createPayment() {
        pipeline.send(new PurchaseCreationRequest("3.99"));
    }
}
