package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.Country;
import com.emt.springbackendapi.repository.CountryRepository;
import com.emt.springbackendapi.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    public List<Country> findAll() {
        return this.countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return this.countryRepository.findById(id);
    }

    @Override
    public Optional<Country> create(String name, String continent) {
        return Optional.of(this.countryRepository.save(new Country(name, continent)));
    }

    @Override
    public Optional<Country> update(Long id, String name, String continent) {
        Country c = this.countryRepository.findById(id).orElse(null);

        if (c != null) {
            c.setName(name);
            c.setContinent(continent);

            return Optional.of(this.countryRepository.save(c));
        } else {
            System.out.println("Country that needed updating not found");
            return Optional.empty();
        }


    }

    @Override
    public void delete(Long id) {
        this.countryRepository.delete(this.countryRepository.findById(id).get());
    }
}
