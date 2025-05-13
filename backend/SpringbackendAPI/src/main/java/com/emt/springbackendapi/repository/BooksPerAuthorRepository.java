package com.emt.springbackendapi.repository;

import com.emt.springbackendapi.model.domain.BooksPerAuthorMView;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Profile("psql")
public interface BooksPerAuthorRepository extends JpaRepository<BooksPerAuthorMView, Long> {
    Optional<BooksPerAuthorMView> findBooksPerAuthorMViewById(Long id);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW public.books_per_author", nativeQuery = true)
    void refreshMaterializedView();
}
