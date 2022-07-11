package com.dpi.publishingapi.features.payment.purchase.create_purchase;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.book.Book;
import com.dpi.publishingapi.data.books.book.BookRepository;
import com.dpi.publishingapi.exceptions.CustomException;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Money;
import com.paypal.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class PurchaseCreationHandler implements Command.Handler<PurchaseCreationRequest, PurchaseCreationResponse> {
    private final PayPalHttpClient paypalClient;
    private final BookRepository bookRepository;

    @Autowired
    public PurchaseCreationHandler(PayPalHttpClient paypalClient, BookRepository bookRepository) {
        this.paypalClient = paypalClient;
        this.bookRepository = bookRepository;
    }


    // extract to separate handler
    private BigDecimal getTotalPurchasePrice(List<Long> bookIds) {
        List<Book> purchaseBooks = bookRepository.findAllById(bookIds);

        if (purchaseBooks.isEmpty()) {
            throw new CustomException("Invalid Cart Items", HttpStatus.BAD_REQUEST);
        }

        return purchaseBooks.stream()
                .map(Book::getPrice)
                .reduce(new BigDecimal(0), (a, b) -> a.add(b));
    }

    @Override
    public PurchaseCreationResponse handle(PurchaseCreationRequest request) {
        BigDecimal price = getTotalPurchasePrice(request.bookIds());
        try {
            Order paypalOrder = new PaypalOrderCreator(
                    paypalClient, new Money()
                    .currencyCode("USD")
                    .value(price.toString()),
                    "CAPTURE")
                    .create();

            return new PurchaseCreationResponse(paypalOrder.id());
        } catch (IOException e) {
            throw new CustomException("Paypal payment creation request failed", HttpStatus.BAD_GATEWAY);
        }
    }
}
