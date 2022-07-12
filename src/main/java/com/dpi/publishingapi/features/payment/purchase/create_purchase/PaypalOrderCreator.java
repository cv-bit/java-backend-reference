package com.dpi.publishingapi.features.payment.purchase.create_purchase;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaypalOrderCreator {

    private final PayPalHttpClient paypalClient;
    private final Money money;
    private final String intent;

    public PaypalOrderCreator(PayPalHttpClient paypalClient, Money money, String intent) {
        this.paypalClient = paypalClient;
        this.money = money;
        this.intent = intent;
    }

    private OrdersCreateRequest createRequest() {
        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>(
                List.of(new PurchaseUnitRequest()
                        .amountWithBreakdown(new AmountWithBreakdown()
                                .currencyCode(money.currencyCode())
                                .value(money.value()))));

        ApplicationContext applicationContext = new ApplicationContext().shippingPreference("NO_SHIPPING")
                .brandName("Digital Publishing Inc");

        OrderRequest orderRequest = new OrderRequest().
                checkoutPaymentIntent(intent)
                .purchaseUnits(purchaseUnits)
                .applicationContext(applicationContext);

        return new OrdersCreateRequest().requestBody(orderRequest);
    }

    public Order create() throws IOException {
        OrdersCreateRequest request = createRequest();

        HttpResponse<Order> orderResponse = paypalClient.execute(request);
        return orderResponse.result();
    }
}
