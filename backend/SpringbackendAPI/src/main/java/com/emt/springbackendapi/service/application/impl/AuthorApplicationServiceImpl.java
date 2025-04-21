package com.emt.springbackendapi.service.application.impl;

import com.emt.springbackendapi.events.AuthorCreatedEvent;
import com.emt.springbackendapi.events.AuthorRemovedEvent;
import com.emt.springbackendapi.events.AuthorUpdatedEvent;
import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.UpdateAuthorDTO;
import com.emt.springbackendapi.model.projections.AuthorFirstLastNameProjection;
import com.emt.springbackendapi.repository.AuthorRepository;
import com.emt.springbackendapi.service.application.AuthorApplicationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {

    private final AuthorRepository authorRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AuthorApplicationServiceImpl(AuthorRepository authorRepository,
                                        ApplicationEventPublisher applicationEventPublisher) {
        this.authorRepository = authorRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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
        applicationEventPublisher.publishEvent(new AuthorCreatedEvent(ret));
        return Optional.of(UpdateAuthorDTO.from(ret));
    }

    @Override
    public Optional<UpdateAuthorDTO> update(Long id, String name, String surname, Country countryOfOrigin) {
        Author target = authorRepository.findById(id).get();

        target.setName(name);
        target.setSurname(surname);
        target.setCountryOfOrigin(countryOfOrigin);

        target = authorRepository.save(target);

        applicationEventPublisher.publishEvent(new AuthorUpdatedEvent(target));
        return Optional.of(UpdateAuthorDTO.from(target));
    }

    @Override
    public void delete(Long id) {
        Author target = authorRepository.findById(id).get();
        applicationEventPublisher.publishEvent(new AuthorRemovedEvent(target));
        authorRepository.delete(target);
    }

    @Override
    public List<AuthorFirstLastNameProjection> getAuthorsFirstAndLastNames() {
        return this.authorRepository.getFirstAndLastNamesOfAuthors();
    }
}
