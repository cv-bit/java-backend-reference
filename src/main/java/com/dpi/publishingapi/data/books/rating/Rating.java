package com.dpi.publishingapi.data.books.rating;

import javax.persistence.*;

@Entity
@Table(name = "book_ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double average;

    private Integer count;

    public Rating(Double average, Integer count) {
        this.average = average;
        this.count = count;
    }

    public Rating() {
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
