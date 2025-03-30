package com.emt.springbackendapi.service.application.impl;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.UpdateAuthorDTO;
import com.emt.springbackendapi.repository.AuthorRepository;
import com.emt.springbackendapi.service.application.AuthorApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {

    private final AuthorRepository authorRepository;

    public AuthorApplicationServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<UpdateAuthorDTO> findAll() {
        List<Author> authorsInternal = authorRepository.findAll();
        return UpdateAuthorDTO.from(authorsInternal);
    }

    @Override
    public Optional<UpdateAuthorDTO> findById(Long id) {
        Author internal = authorRepository.findById(id).get();
        return Optional.of(UpdateAuthorDTO.from(internal));
    }

    @Override
    public Optional<UpdateAuthorDTO> create(String name, String surname, Country countryOfOrigin) {
        Author ret = authorRepository.save(new Author(name, surname, countryOfOrigin));
        return Optional.of(UpdateAuthorDTO.from(ret));
    }

    @Override
    public Optional<UpdateAuthorDTO> update(Long id, String name, String surname, Country countryOfOrigin) {
        Author target = authorRepository.findById(id).get();

        target.setName(name);
        target.setSurname(surname);
        target.setCountryOfOrigin(countryOfOrigin);


        return Optional.of(UpdateAuthorDTO.from(authorRepository.save(target)));
    }

    @Override
    public void delete(Long id) {
        Author target = authorRepository.findById(id).get();
        authorRepository.delete(target);
    }
}
