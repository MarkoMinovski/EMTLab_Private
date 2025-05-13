package com.emt.springbackendapi.service;

import com.emt.springbackendapi.model.domain.BooksPerAuthorMView;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

@Profile("psql")
public interface BooksPerAuthorService {
    public Optional<BooksPerAuthorMView> findBooksPerAuthorRecordByAuthorId(Long id);

    void refreshMaterializedView();

    public List<BooksPerAuthorMView> findAll();
}
