package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.domain.AuthorsPerCountryMView;
import com.emt.springbackendapi.repository.AuthorsPerCountryRepository;
import com.emt.springbackendapi.service.AuthorsPerCountryService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("psql")
public class AuthorsPerCountryServiceImpl implements AuthorsPerCountryService {

    private final AuthorsPerCountryRepository authorsPerCountryRepository;

    public AuthorsPerCountryServiceImpl(AuthorsPerCountryRepository authorsPerCountryRepository) {
        this.authorsPerCountryRepository = authorsPerCountryRepository;
    }

    @Override
    public Optional<AuthorsPerCountryMView> FindAuthorsPerCountryByCountryName(String name) {
        return authorsPerCountryRepository.findById(name);
    }

    @Override
    public void refreshMaterializedView() {
        authorsPerCountryRepository.refreshMaterializedView();
    }

    @Override
    public List<AuthorsPerCountryMView> findAll() {
        return authorsPerCountryRepository.findAll();
    }


}
