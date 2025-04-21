package com.emt.springbackendapi.repository;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.projections.AuthorFirstLastNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "SELECT a.name, a.surname FROM public.author as a", nativeQuery = true)
    List<AuthorFirstLastNameProjection> getFirstAndLastNamesOfAuthors();
}
