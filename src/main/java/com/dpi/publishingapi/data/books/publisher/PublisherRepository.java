package com.dpi.publishingapi.data.books.publisher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("SELECT DISTINCT p from Publisher p GROUP BY p.name")
    List<Publisher> findDistinctByName();
}
