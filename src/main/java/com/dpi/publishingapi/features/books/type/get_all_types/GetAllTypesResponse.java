package com.dpi.publishingapi.features.books.type.get_all_types;

import com.dpi.publishingapi.data.books.type.Type;

import java.util.List;

public record GetAllTypesResponse(List<Type> types) {
}
