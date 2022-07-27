package com.dpi.publishingapi.data.books.creator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreatorRepository extends JpaRepository<Creator, Long> {

    @Query("SELECT DISTINCT c FROM Creator c GROUP BY c.name")
    List<Creator> findDistinctByName();
}
