package com.dpi.publishingapi.features.books.creator.get_all_creators;

import com.dpi.publishingapi.data.books.creator.Creator;

import java.util.List;

public record GetAllCreatorsResponse(List<Creator> creators) {
}
