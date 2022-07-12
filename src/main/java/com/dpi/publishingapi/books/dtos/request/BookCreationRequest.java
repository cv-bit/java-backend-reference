package com.dpi.publishingapi.books.dtos.request;

import java.util.List;

// called from authoring tool when book is created
// uses old gbm book data format

public record BookCreationRequest(String title, Long externalBookId, String coverUrl, String description, String bookDataUrl, String publisher, String sampleVideoUrl, List<String> languages, String difficulty, String primaryColor, String secondaryColor, String thirdPartyPdfUrl, String dpiPdfUrl, String pdfCoupon, List<CreatorDTO> creators) {
}