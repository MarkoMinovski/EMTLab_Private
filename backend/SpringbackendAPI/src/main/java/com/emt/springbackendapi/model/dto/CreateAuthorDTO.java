package com.emt.springbackendapi.model.dto;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Country;

import java.util.List;

public record CreateAuthorDTO(
        String name,
        String surname,
        Long country
) {

    public static CreateAuthorDTO from(Author author) {
        return new CreateAuthorDTO(
                author.getName(),
                author.getSurname(),
                author.getCountryOfOrigin().getId()
        );
    }

    public static List<CreateAuthorDTO> from(List<Author> authors) {
        return authors.stream().map(CreateAuthorDTO::from).toList();
    }

    public Author toAuthor(Country country) {
        return new Author(this.name, this.surname, country);
    }
}


