package com.dpi.publishingapi.payment.purchase.create;

import an.awesome.pipelinr.Command;
import com.paypal.orders.Order;

public class PurchaseCreationRequest implements Command<Order> {
    public final String price;

    public PurchaseCreationRequest(String price) {
        this.price = price;
    }
}