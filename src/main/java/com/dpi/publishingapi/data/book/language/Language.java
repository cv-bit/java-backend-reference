package com.dpi.publishingapi.data.book.language;

import com.dpi.publishingapi.data.book.book.Book;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "languages")
    private Set<Book> books;

    @Enumerated(EnumType.STRING)
    private ELanguage language;

    public Language() {
    }

    public Language(ELanguage language) {
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
