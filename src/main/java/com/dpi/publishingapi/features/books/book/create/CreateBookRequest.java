package com.dpi.publishingapi.features.books.book.create;

import an.awesome.pipelinr.Command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

// called from authoring tool when book is created
// uses old gbm book data format

public record CreateBookRequest(
        @NotBlank
        String title,
        @NotNull
        Long externalBookId,
        @NotBlank
        String coverUrl,
        @NotBlank
        String description,
        @NotBlank
        String bookDataUrl,
        @NotBlank
        String publisher,
        @NotBlank
        String sampleVideoUrl,
        @NotEmpty
        List<String> languages,
        @NotBlank
        String difficulty,
        @NotBlank
        String primaryColor,
        @NotBlank
        String secondaryColor,
        @NotBlank
        String thirdPartyPdfUrl,
        @NotBlank
        String dpiPdfUrl,
        @NotBlank
        String pdfCoupon,
        @NotEmpty
        List<CreatorDTO> creators) implements Command<CreateBookResponse> {
}