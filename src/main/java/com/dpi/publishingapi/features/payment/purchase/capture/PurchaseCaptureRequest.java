package com.dpi.publishingapi.features.payment.purchase.capture;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public record PurchaseCaptureRequest(
        @NotNull
        @Size(min = 19, max = 19)
        String orderId,
        @NotEmpty
        List<Long> bookIds) implements Command<PurchaseCaptureResponse> {
}
