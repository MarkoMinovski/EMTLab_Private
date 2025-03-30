package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.repository.AuthorRepository;
import com.emt.springbackendapi.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public Optional<Author> create(String name, String surname, Country countryOfOrigin) {
        return Optional.of(this.authorRepository.save(new Author(name, surname, countryOfOrigin)));
    }

    @Override
    public Optional<Author> update(Long id, String name, String surname, Country countryOfOrigin) {
        Author a = this.authorRepository.findById(id).orElse(null);

        if (a != null) {
            a.setName(name);
            a.setSurname(surname);
            a.setCountryOfOrigin(countryOfOrigin);

            return Optional.of(this.authorRepository.save(a));
        } else {
            System.out.println("Author that needed updating not found!");
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        this.authorRepository.delete(this.authorRepository.findById(id).get());
    }
}
