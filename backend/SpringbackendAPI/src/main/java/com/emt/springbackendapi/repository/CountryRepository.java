package com.emt.springbackendapi.repository;

import com.emt.springbackendapi.model.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findCountriesByNameContainingIgnoreCase(String name);

    List<Country> findCountriesByContinentEqualsIgnoreCase(String continent);
}
