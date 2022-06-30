package com.dpi.publishingapi.books;

import javax.persistence.*;

@Entity
@Table(name = "book_pdf_information")
public class PdfInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "third_party_pdf_store_url")
    private String thirdPartyPdfUrl;

    @Column(name = "dpi_pdf_url")
    private String dpiPdfUrl;

    @Column(name = "pdf_coupon")
    private String pdfCoupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
