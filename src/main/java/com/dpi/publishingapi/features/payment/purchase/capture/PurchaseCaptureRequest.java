package com.dpi.publishingapi.features.payment.purchase.capture;

import an.awesome.pipelinr.Command;

public record PurchaseCaptureRequest(String orderId) implements Command<PurchaseCaptureResponse> {
}
