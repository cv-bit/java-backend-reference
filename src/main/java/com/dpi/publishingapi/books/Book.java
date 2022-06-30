package com.dpi.publishingapi.books;

import com.dpi.publishingapi.books.language.Language;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "external_book_id")
    private Long externalBookId;

    private String coverUrl;
    private String description;
    private String bookDataUrl;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "sample_video_url")
    private String sampleVideoUrl;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_language", joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "language_id")})
    private Set<Language> languages = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pdf_info_id", referencedColumnName = "id")
    private PdfInfo pdfInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getExternalBookId() {
        return externalBookId;
    }

    public void setExternalBookId(Long externalBookId) {
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

    public String getSampleVideoUrl() {
        return sampleVideoUrl;
    }

    public void setSampleVideoUrl(String sampleVideoUrl) {
        this.sampleVideoUrl = sampleVideoUrl;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public PdfInfo getPdfInfo() {
        return pdfInfo;
    }

    public void setPdfInfo(PdfInfo pdfInfo) {
        this.pdfInfo = pdfInfo;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }


}
