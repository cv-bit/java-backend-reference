package com.dpi.publishingapi.features.payment.purchase.capture;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.exceptions.CustomException;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersCaptureRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PurchaseCaptureHandler implements Command.Handler<PurchaseCaptureRequest, PurchaseCaptureResponse> {

    private final PayPalHttpClient paypalClient;

    @Autowired
    public PurchaseCaptureHandler(PayPalHttpClient paypalClient) {
        this.paypalClient = paypalClient;
    }

    public final Validator<PurchaseCaptureRequest> validator = ValidatorBuilder.<PurchaseCaptureRequest>of()
            .constraint(PurchaseCaptureRequest::orderId, "orderId", id -> id.notBlank().fixedSize(17)
                    .message("Paypal order id is invalid"))
            .build();

    @Override
    public PurchaseCaptureResponse handle(PurchaseCaptureRequest purchaseCaptureRequest) {
        OrdersCaptureRequest request = new OrdersCaptureRequest(purchaseCaptureRequest.orderId());
        try {
            Order response = paypalClient.execute(request).result();
        } catch (IOException e) {
            throw new CustomException("Paypal payment request failed", HttpStatus.BAD_GATEWAY);
        }
        return new PurchaseCaptureResponse("Purchase was successful");
    }
}
