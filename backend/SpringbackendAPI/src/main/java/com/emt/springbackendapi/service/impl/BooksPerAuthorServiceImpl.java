package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.domain.BooksPerAuthorMView;
import com.emt.springbackendapi.repository.BooksPerAuthorRepository;
import com.emt.springbackendapi.service.BooksPerAuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksPerAuthorServiceImpl implements BooksPerAuthorService {

    private final BooksPerAuthorRepository booksPerAuthorRepository;

    public BooksPerAuthorServiceImpl(BooksPerAuthorRepository booksPerAuthorRepository) {
        this.booksPerAuthorRepository = booksPerAuthorRepository;
    }

    @Override
    public Optional<BooksPerAuthorMView> findBooksPerAuthorRecordByAuthorId(Long id) {
        return this.booksPerAuthorRepository.findBooksPerAuthorMViewById(id);
    }

    @Override
    public void refreshMaterializedView() {
        System.out.print("Refreshing materialized View from inside service\n");
        booksPerAuthorRepository.refreshMaterializedView();
    }

    @Override
    public List<BooksPerAuthorMView> findAll() {
        return this.booksPerAuthorRepository.findAll();
    }
}
