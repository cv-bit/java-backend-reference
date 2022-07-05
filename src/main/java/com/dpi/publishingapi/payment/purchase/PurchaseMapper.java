package com.dpi.publishingapi.payment.purchase;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    PurchaseDto toDto(Purchase purchase);

    Purchase toPurchase(PurchaseDto purchaseDto);
}
