package com.dpi.publishingapi.payment.purchase;

import java.math.BigDecimal;
import java.time.Instant;

public record PurchaseDto(Long id, BigDecimal price, Instant timestamp) {
}
