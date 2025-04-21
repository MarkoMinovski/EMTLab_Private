package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.domain.AuthorsPerCountryMView;
import com.emt.springbackendapi.repository.AuthorsPerCountryRepository;
import com.emt.springbackendapi.service.AuthorsPerCountryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
}
