package com.dpi.publishingapi.features.books.book.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public record SimpleBookDto(
        String title,
        String coverUrl,
        Long externalBookId,
        BigDecimal price,
        String creatorNames) {

    public static SimpleBookDto mapFromSqlObject(Object[] object) {
        return new SimpleBookDto(
                (String) object[0],
                (String) object[1],
                ((BigInteger) object[2]).longValue(),
                (BigDecimal) object[3],
                (String) object[4]
        );
    }
}

