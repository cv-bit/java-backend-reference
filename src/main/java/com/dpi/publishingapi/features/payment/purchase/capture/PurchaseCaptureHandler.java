package com.dpi.publishingapi.features.payment.purchase.capture;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.book.book.Book;
import com.dpi.publishingapi.data.book.book.BookRepository;
import com.dpi.publishingapi.data.payment.purchase.Purchase;
import com.dpi.publishingapi.data.user.User;
import com.dpi.publishingapi.data.user.UserRepository;
import com.dpi.publishingapi.exceptions.CustomException;
import com.dpi.publishingapi.security.user.UserDetailsImpl;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Capture;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersCaptureRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;

@Component
public class PurchaseCaptureHandler implements Command.Handler<PurchaseCaptureRequest, PurchaseCaptureResponse> {

    private final PayPalHttpClient paypalClient;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PurchaseCaptureHandler(PayPalHttpClient paypalClient, UserRepository userRepository, BookRepository bookRepository) {
        this.paypalClient = paypalClient;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    private Order captureOrder(String orderId) {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        try {
            return paypalClient.execute(request).result();
        } catch (IOException e) {
            throw new CustomException("Paypal payment request failed", HttpStatus.BAD_GATEWAY);
        }
    }

    @Transactional
    void setPurchaseInfo(List<Long> bookIds, User user, Order order) {
        String status = order.status();
        Capture capture = order.purchaseUnits().get(0).payments().captures().get(0);
        BigDecimal paymentPrice = new BigDecimal(capture.amount().value());
        Timestamp timestamp = Timestamp.from(Instant.parse(capture.createTime()));

        if (!status.equals("COMPLETED")) {
            throw new CustomException("Paypal Order failed", HttpStatus.BAD_GATEWAY);
        }

        List<Book> cartBooks = bookRepository.findAllById(bookIds);
        if (cartBooks.isEmpty()) {
            throw new CustomException("Cart is empty", HttpStatus.BAD_REQUEST);
        }

        Purchase purchase = new Purchase(paymentPrice, timestamp, new HashSet<>(cartBooks));
        user.getPurchases().add(purchase);
        user.getLibrary().addAll(cartBooks);
        userRepository.save(user);
    }

    @Override
    public PurchaseCaptureResponse handle(PurchaseCaptureRequest purchaseCaptureRequest) {
        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        Order order = captureOrder(purchaseCaptureRequest.orderId());

        setPurchaseInfo(purchaseCaptureRequest.bookIds(), user, order);

        return new PurchaseCaptureResponse("Purchase was successful");
    }
}
