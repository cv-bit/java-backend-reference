package com.dpi.publishingapi.features.payment.purchase.create;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.books.book.Book;
import com.dpi.publishingapi.books.book.BookRepository;
import com.dpi.publishingapi.exceptions.CustomException;
import com.dpi.publishingapi.features.payment.purchase.PaypalOrder;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseCreationHandler implements Command.Handler<PurchaseCreationRequest, PurchaseCreationResponse> {
    private final PayPalHttpClient paypalClient;
    private BookRepository bookRepository;

    @Autowired
    public PurchaseCreationHandler(PayPalHttpClient paypalClient, BookRepository bookRepository) {
        this.paypalClient = paypalClient;
        this.bookRepository = bookRepository;
    }

    public final Validator<PurchaseCreationRequest> validator = ValidatorBuilder.<PurchaseCreationRequest>of()
            .constraint(PurchaseCreationRequest::bookIds, "bookIds", b -> b.notNull().message("No book ids provided"))
            .forEach(PurchaseCreationRequest::bookIds, "bookIds", b -> b._long(Long::longValue, "id", l -> l.notNull().oneOf(
                            bookRepository.findAll().stream().map(Book::getId).collect(Collectors.toList()))
                    .message("Book does not exist")))
            .build();

    // extract to seperate handler
    private BigDecimal getTotalPurchasePrice(List<Long> bookIds) {
        List<Book> purchaseBooks = bookRepository.findAllById(bookIds);

        return purchaseBooks.stream()
                .map(Book::getPrice)
                .reduce(new BigDecimal(0), (a, b) -> a.add(b));
    }

    @Override
    public PurchaseCreationResponse handle(PurchaseCreationRequest request) {
        BigDecimal price = getTotalPurchasePrice(request.bookIds());
        try {
            return new PurchaseCreationResponse(
                    new PaypalOrder("USD", price, paypalClient)
                            .execute());
        } catch (IOException e) {
            throw new CustomException("Paypal payment request failed", HttpStatus.BAD_GATEWAY);
        }
    }
}
