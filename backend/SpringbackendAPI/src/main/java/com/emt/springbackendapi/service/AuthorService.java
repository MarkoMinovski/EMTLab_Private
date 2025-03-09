package com.emt.springbackendapi.service;

import com.emt.springbackendapi.model.Author;
import com.emt.springbackendapi.model.Country;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(Long id);

    Optional<Author> create(String name, String surname, Country countryOfOrigin);

    Optional<Author> update(Long id, String name, String surname, Country countryOfOrigin);

    void delete(Long id);
}
