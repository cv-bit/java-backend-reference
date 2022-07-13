package com.dpi.publishingapi.features.payment.purchase;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.features.payment.purchase.capture_purchase.PurchaseCaptureRequest;
import com.dpi.publishingapi.features.payment.purchase.capture_purchase.PurchaseCaptureResponse;
import com.dpi.publishingapi.features.payment.purchase.create_purchase.PurchaseCreationRequest;
import com.dpi.publishingapi.features.payment.purchase.create_purchase.PurchaseCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PurchaseController {

    private final Pipeline pipeline;

    @Autowired
    public PurchaseController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PurchaseCreationResponse createPayment(@RequestBody PurchaseCreationRequest purchaseCreationRequest) {
        return pipeline.send(purchaseCreationRequest);
    }

    @PostMapping("/capture")
    public PurchaseCaptureResponse capturePayment(@RequestBody PurchaseCaptureRequest purchaseCaptureRequest) {
        return pipeline.send(purchaseCaptureRequest);
    }
}
