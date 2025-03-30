package com.emt.springbackendapi.service.application;

import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.UpdateCountryDTO;

import java.util.List;
import java.util.Optional;

public interface CountryApplicationService {
    List<UpdateCountryDTO> findAll();

    Optional<UpdateCountryDTO> findById(Long id);

    Optional<UpdateCountryDTO> create(String name, String continent);

    Optional<UpdateCountryDTO> update(Long id, String name, String continent);

    void delete(Long id);

    List<UpdateCountryDTO> findByNameContainingString(String s);

    List<UpdateCountryDTO> findByContinent(String continent);
}
