package com.dpi.publishingapi.features.payment.purchase;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.features.payment.purchase.capture_purchase.PurchaseCaptureRequest;
import com.dpi.publishingapi.features.payment.purchase.capture_purchase.PurchaseCaptureResponse;
import com.dpi.publishingapi.features.payment.purchase.create_purchase.PurchaseCreationRequest;
import com.dpi.publishingapi.features.payment.purchase.create_purchase.PurchaseCreationResponse;
import com.dpi.publishingapi.misc.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<PurchaseCreationResponse> createPayment(@RequestBody PurchaseCreationRequest purchaseCreationRequest) {
        PurchaseCreationResponse response = pipeline.send(purchaseCreationRequest);
        return new ResponseEntity<PurchaseCreationResponse>(response, HttpStatus.CREATED);
    }

    @PostMapping("/capture")
    public ResponseEntity<MessageResponse> capturePayment(@RequestBody PurchaseCaptureRequest purchaseCaptureRequest) {
        PurchaseCaptureResponse response = pipeline.send(purchaseCaptureRequest);
        return new ResponseEntity<MessageResponse>(new MessageResponse(response.message()), HttpStatus.CREATED);
    }
}
