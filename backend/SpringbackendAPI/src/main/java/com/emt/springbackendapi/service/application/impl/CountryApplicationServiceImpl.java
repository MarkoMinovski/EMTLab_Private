package com.emt.springbackendapi.service.application.impl;

import com.emt.springbackendapi.model.dto.UpdateCountryDTO;
import com.emt.springbackendapi.service.application.CountryApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryApplicationServiceImpl implements CountryApplicationService {
    @Override
    public List<UpdateCountryDTO> findAll() {
        return List.of();
    }

    @Override
    public Optional<UpdateCountryDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UpdateCountryDTO> create(String name, String continent) {
        return Optional.empty();
    }

    @Override
    public Optional<UpdateCountryDTO> update(Long id, String name, String continent) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UpdateCountryDTO> findByNameContainingString(String s) {
        return List.of();
    }

    @Override
    public List<UpdateCountryDTO> findByContinent(String continent) {
        return List.of();
    }
}
