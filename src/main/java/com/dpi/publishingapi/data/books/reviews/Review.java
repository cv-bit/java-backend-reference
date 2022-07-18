package com.dpi.publishingapi.data.books.reviews;

import com.dpi.publishingapi.data.auth.user.User;
import com.dpi.publishingapi.data.books.book.Book;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String review;

    public Review(String displayName, User user, Book book, Double rating, String review) {
        this.displayName = displayName;
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.review = review;
    }

    public Review() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
