package com.emt.springbackendapi.service;

import com.emt.springbackendapi.model.Author;
import com.emt.springbackendapi.model.Book;
import com.emt.springbackendapi.model.enums.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> create(String name, Category category, Integer availableCopies, Author a);
    Optional<Book> update(Long id, String name, Category category, Author a);
    void delete(Long id);
    void setUnborrowable(Long id);
}
