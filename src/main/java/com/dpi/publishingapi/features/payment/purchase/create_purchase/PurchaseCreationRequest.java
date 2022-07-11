package com.dpi.publishingapi.features.payment.purchase.create_purchase;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public record PurchaseCreationRequest(@NotEmpty List<Long> bookIds) implements Command<PurchaseCreationResponse> {
}