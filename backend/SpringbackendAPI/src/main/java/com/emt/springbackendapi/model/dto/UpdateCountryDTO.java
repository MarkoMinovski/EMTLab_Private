package com.emt.springbackendapi.model.dto;

import com.emt.springbackendapi.model.domain.Country;

import java.util.List;

public record UpdateCountryDTO(
        Long id,
        String name,
        String continent
) {

    public static UpdateCountryDTO from(Country c) {
        return new UpdateCountryDTO(c.getId(), c.getName(), c.getContinent());
    }

    public static List<UpdateCountryDTO> fromList(List<Country> countries) {
        return countries.stream().map(UpdateCountryDTO::from).toList();
    }

    public Country toCountry() {
        return new Country(this.name, this.continent);
    }
}
