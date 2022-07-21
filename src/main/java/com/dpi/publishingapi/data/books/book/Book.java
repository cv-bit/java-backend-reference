package com.dpi.publishingapi.data.books.book;

import com.dpi.publishingapi.data.books.creator.Creator;
import com.dpi.publishingapi.data.books.difficulty.Difficulty;
import com.dpi.publishingapi.data.books.language.Language;
import com.dpi.publishingapi.data.books.pdfinfo.PdfInfo;
import com.dpi.publishingapi.data.books.publisher.Publisher;
import com.dpi.publishingapi.data.books.reviews.Review;
import com.dpi.publishingapi.data.books.theme.Theme;
import com.dpi.publishingapi.data.books.type.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = "external_book_id")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "external_book_id")
    private Long externalBookId;

    private String coverUrl;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String bookDataUrl;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "sample_video_url")
    private String sampleVideoUrl;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "book_language", inverseJoinColumns = @JoinColumn(name = "language_id"), joinColumns = @JoinColumn(name = "book_id"))
    private Set<Language> languages;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "book_creators", inverseJoinColumns = @JoinColumn(name = "creator_id"), joinColumns = @JoinColumn(name = "book_id"))
    private Set<Creator> creators;

    @OneToMany(mappedBy = "book")
    private Set<Review> reviews;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id_map", referencedColumnName = "id")
    private Theme theme;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pdf_info_id", referencedColumnName = "id")
    private PdfInfo pdfInfo;

    @Column(precision = 15, scale = 2)
    private BigDecimal price;

    public Book() {
    }


    public Book(String title, Long externalBookId, String coverUrl, String description, String bookDataUrl, Publisher publisher, String sampleVideoUrl, Set<Language> languages, Set<Creator> creators, Type type, Difficulty difficulty, Theme theme, PdfInfo pdfInfo, BigDecimal price) {
        this.title = title;
        this.externalBookId = externalBookId;
        this.coverUrl = coverUrl;
        this.description = description;
        this.bookDataUrl = bookDataUrl;
        this.publisher = publisher;
        this.sampleVideoUrl = sampleVideoUrl;
        this.languages = languages;
        this.creators = creators;
        this.type = type;
        this.difficulty = difficulty;
        this.theme = theme;
        this.pdfInfo = pdfInfo;
        this.price = price;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<Creator> getCreators() {
        return creators;
    }

    public void setCreators(Set<Creator> creators) {
        this.creators = creators;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(externalBookId, book.externalBookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, externalBookId);
    }
}
