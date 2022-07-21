package com.dpi.publishingapi.data.books.book;

import com.dpi.publishingapi.data.books.creator.Creator;

import java.math.BigDecimal;
import java.util.Set;

public interface SimpleBook {
    String getTitle();

    String getCoverUrl();

    Long getExternalBookId();

    BigDecimal getPrice();

    Set<Creator> getCreators();
}
