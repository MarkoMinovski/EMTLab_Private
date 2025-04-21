package com.emt.springbackendapi.service;

import com.emt.springbackendapi.model.domain.AuthorsPerCountryMView;

import java.util.Optional;

public interface AuthorsPerCountryService {
    Optional<AuthorsPerCountryMView> FindAuthorsPerCountryByCountryName(String name);

    void refreshMaterializedView();
}
