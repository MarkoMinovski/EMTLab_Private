package com.emt.springbackendapi.service.application.impl;

import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.UpdateCountryDTO;
import com.emt.springbackendapi.repository.CountryRepository;
import com.emt.springbackendapi.service.application.CountryApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryApplicationServiceImpl implements CountryApplicationService {

    private final CountryRepository countryRepository;

    public CountryApplicationServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    public List<UpdateCountryDTO> findAll() {
        List<Country> internal = this.countryRepository.findAll();

        return UpdateCountryDTO.fromList(internal);
    }

    @Override
    public Optional<UpdateCountryDTO> findById(Long id) {
        return Optional.of(UpdateCountryDTO.from(countryRepository.findById(id).get()));
    }

    @Override
    public Optional<UpdateCountryDTO> create(String name, String continent) {
        Country internal = countryRepository.save(new Country(name, continent));
        return Optional.of(UpdateCountryDTO.from(internal));
    }

    @Override
    public Optional<UpdateCountryDTO> update(Long id, String name, String continent) {
        Country internal = countryRepository.findById(id).get();

        internal.setName(name);
        internal.setContinent(continent);

        internal = countryRepository.save(internal);

        return Optional.of(UpdateCountryDTO.from(internal));
    }

    @Override
    public void delete(Long id) {
        Country internal = countryRepository.findById(id).get();
        countryRepository.delete(internal);
    }

    @Override
    public List<UpdateCountryDTO> findByNameContainingString(String s) {
        List<Country> internalListResult = countryRepository.findCountriesByNameContainingIgnoreCase(s);
        return UpdateCountryDTO.fromList(internalListResult);
    }

    @Override
    public List<UpdateCountryDTO> findByContinent(String continent) {
        List<Country> internalListResult = countryRepository.findCountriesByContinentEqualsIgnoreCase(continent);
        return UpdateCountryDTO.fromList(internalListResult);
    }
}
