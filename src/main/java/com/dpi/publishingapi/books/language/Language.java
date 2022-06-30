package com.dpi.publishingapi.books.language;

import com.dpi.publishingapi.books.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "language")
public class Language {
    @ManyToMany(mappedBy = "languages")
    private final Set<Book> books = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ELanguage language;


    public Language() {
    }

    public Language(Long id, ELanguage language) {
        this.id = id;
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
}
