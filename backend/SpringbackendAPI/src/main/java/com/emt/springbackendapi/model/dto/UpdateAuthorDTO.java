package com.emt.springbackendapi.model.dto;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Country;

import java.util.List;

public record UpdateAuthorDTO(
        Long id,
        String name,
        String surname,
        Long country
) {

    public static UpdateAuthorDTO from(Author author) {
        return new UpdateAuthorDTO(
                author.getId(),
                author.getName(),
                author.getSurname(),
                author.getCountryOfOrigin().getId()
        );
    }

    public static List<UpdateAuthorDTO> from(List<Author> authors) {
        return authors.stream().map(UpdateAuthorDTO::from).toList();
    }

    public Author toAuthor(Country country) {
        return new Author(this.name, this.surname, country);
    }
}
