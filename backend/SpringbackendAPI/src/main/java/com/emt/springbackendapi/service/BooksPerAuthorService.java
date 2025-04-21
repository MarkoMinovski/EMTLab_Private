package com.emt.springbackendapi.service;

import com.emt.springbackendapi.model.domain.BooksPerAuthorMView;

import java.util.List;
import java.util.Optional;

public interface BooksPerAuthorService {
    public Optional<BooksPerAuthorMView> findBooksPerAuthorRecordByAuthorId(Long id);

    void refreshMaterializedView();

    public List<BooksPerAuthorMView> findAll();
}
