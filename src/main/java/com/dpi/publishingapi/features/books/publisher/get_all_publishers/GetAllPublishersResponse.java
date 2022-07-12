package com.dpi.publishingapi.features.books.publisher.get_all_publishers;

import com.dpi.publishingapi.data.books.publisher.Publisher;

import java.util.List;

public record GetAllPublishersResponse(List<Publisher> publishers) {
}
