package com.dpi.publishingapi.data.books.creator;

import com.dpi.publishingapi.data.books.book.Book;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CreatorType creatorType;

    @ManyToMany(mappedBy = "creators")
    private Set<Book> books;

    public Creator(String name, CreatorType creatorType) {
        this.name = name;
        this.creatorType = creatorType;
    }

    public Creator() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreatorType getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(CreatorType creatorType) {
        this.creatorType = creatorType;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
