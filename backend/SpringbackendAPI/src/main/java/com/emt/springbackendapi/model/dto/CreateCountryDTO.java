package com.emt.springbackendapi.model.dto;

import com.emt.springbackendapi.model.domain.Country;

import java.util.List;

public record CreateCountryDTO(
        String name,
        String continent
) {

    public static CreateCountryDTO from(Country c) {
        return new CreateCountryDTO(c.getName(), c.getContinent());
    }

    public static List<CreateCountryDTO> fromList(List<Country> countries) {
        return countries.stream().map(CreateCountryDTO::from).toList();
    }

    public Country toCountry() {
        return new Country(this.name, this.continent);
    }
}
