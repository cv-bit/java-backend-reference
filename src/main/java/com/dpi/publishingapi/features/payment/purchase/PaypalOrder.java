package com.dpi.publishingapi.features.payment.purchase;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaypalOrder {

    private final String currencyCode;
    private final BigDecimal price;
    private final PayPalHttpClient paypalClient;

    public PaypalOrder(String currencyCode, BigDecimal price, PayPalHttpClient paypalClient) {
        this.currencyCode = currencyCode;
        this.price = price;
        this.paypalClient = paypalClient;
    }

    private OrdersCreateRequest createRequest() {
        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>(
                List.of(new PurchaseUnitRequest()
                        .amountWithBreakdown(new AmountWithBreakdown()
                                .currencyCode(currencyCode)
                                .value(String.valueOf(price)))));

        OrderRequest orderRequest = new OrderRequest().
                checkoutPaymentIntent("CAPTURE")
                .purchaseUnits(purchaseUnits);

        return new OrdersCreateRequest().requestBody(orderRequest);
    }

    public Order execute() throws IOException {
        OrdersCreateRequest request = createRequest();

        HttpResponse<Order> orderResponse = paypalClient.execute(request);
        return orderResponse.result();
    }
}
