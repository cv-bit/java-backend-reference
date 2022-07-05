package com.dpi.publishingapi.payment.purchase.create;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.exceptions.CustomException;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseCreationHandler implements Command.Handler<PurchaseCreationRequest, Order> {
    private PayPalHttpClient paypalClient;
    private String price;

    private OrdersCreateRequest createOrderRequest(String price) {
        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>(
                List.of(new PurchaseUnitRequest()
                        .amountWithBreakdown(new AmountWithBreakdown()
                                .currencyCode("USD")
                                .value(price))));

        OrderRequest orderRequest = new OrderRequest().
                checkoutPaymentIntent("CAPTURE")
                .purchaseUnits(purchaseUnits);

        return new OrdersCreateRequest().requestBody(orderRequest);
    }

    @Override
    public Order handle(PurchaseCreationRequest request) {

        OrdersCreateRequest orderRequest = createOrderRequest(price);

        try {
            HttpResponse<Order> orderResponse = paypalClient.execute(orderRequest);
            return orderResponse.result();
        } catch (IOException e) {
            throw new CustomException("Could not create order", HttpStatus.BAD_GATEWAY);
        }
    }
}
