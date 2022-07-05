package com.dpi.publishingapi.payment.purchase;


import com.dpi.publishingapi.books.book.Book;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 15, scale = 2)
    private BigDecimal price;

    private Timestamp timestamp;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "purchases_books", joinColumns = @JoinColumn(name = "purchase_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private final Set<Book> books = new HashSet<>();


    public Purchase(Long id, BigDecimal price, Timestamp timestamp) {
        this.id = id;
        this.price = price;
        this.timestamp = timestamp;
    }

    public Purchase() {

    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
