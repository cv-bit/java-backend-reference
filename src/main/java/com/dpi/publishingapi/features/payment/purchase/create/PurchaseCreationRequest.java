package com.dpi.publishingapi.features.payment.purchase.create;

import an.awesome.pipelinr.Command;

import java.util.List;

public record PurchaseCreationRequest(List<Long> bookIds) implements Command<PurchaseCreationResponse> {
}