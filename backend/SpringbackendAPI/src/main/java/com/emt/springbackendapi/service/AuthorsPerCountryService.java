package com.emt.springbackendapi.service;

import com.emt.springbackendapi.model.domain.AuthorsPerCountryMView;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

@Profile("psql")
public interface AuthorsPerCountryService {
    Optional<AuthorsPerCountryMView> FindAuthorsPerCountryByCountryName(String name);

    void refreshMaterializedView();

    List<AuthorsPerCountryMView> findAll();
}
