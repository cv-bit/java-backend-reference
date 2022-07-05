package com.dpi.publishingapi.books.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

// called from authoring tool when book is created
// uses old gbm book data format
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookCreationRequest {

    private String title;
    private int externalBookId;
    private String coverUrl;
    private String description;
    private String bookDataUrl;
    private String publisher;
    private String sampleVideoUrl;
    private List<String> languages;
    private String difficulty;
    private String primaryColor;
    private String secondaryColor;
    private String thirdPartyPdfUrl;
    private String dpiPdfUrl;
    private String pdfCoupon;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getExternalBookId() {
        return externalBookId;
    }

    public void setExternalBookId(int externalBookId) {
        this.externalBookId = externalBookId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookDataUrl() {
        return bookDataUrl;
    }

    public void setBookDataUrl(String bookDataUrl) {
        this.bookDataUrl = bookDataUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSampleVideoUrl() {
        return sampleVideoUrl;
    }

    public void setSampleVideoUrl(String sampleVideoUrl) {
        this.sampleVideoUrl = sampleVideoUrl;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getThirdPartyPdfUrl() {
        return thirdPartyPdfUrl;
    }

    public void setThirdPartyPdfUrl(String thirdPartyPdfUrl) {
        this.thirdPartyPdfUrl = thirdPartyPdfUrl;
    }

    public String getDpiPdfUrl() {
        return dpiPdfUrl;
    }

    public void setDpiPdfUrl(String dpiPdfUrl) {
        this.dpiPdfUrl = dpiPdfUrl;
    }

    public String getPdfCoupon() {
        return pdfCoupon;
    }

    public void setPdfCoupon(String pdfCoupon) {
        this.pdfCoupon = pdfCoupon;
    }
}