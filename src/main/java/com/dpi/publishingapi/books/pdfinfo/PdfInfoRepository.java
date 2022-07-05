package com.dpi.publishingapi.books.pdfinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfInfoRepository extends JpaRepository<PdfInfo, Long> {
}
