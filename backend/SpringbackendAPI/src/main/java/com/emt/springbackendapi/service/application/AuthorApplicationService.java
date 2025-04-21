package com.emt.springbackendapi.service.application;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.UpdateAuthorDTO;
import com.emt.springbackendapi.model.projections.AuthorFirstLastNameProjection;

import java.util.List;
import java.util.Optional;

public interface AuthorApplicationService {
    List<UpdateAuthorDTO> findAll();

    Optional<UpdateAuthorDTO> findById(Long id);

    Optional<UpdateAuthorDTO> create(String name, String surname, Country countryOfOrigin);

    Optional<UpdateAuthorDTO> update(Long id, String name, String surname, Country countryOfOrigin);

    void delete(Long id);

    List<AuthorFirstLastNameProjection> getAuthorsFirstAndLastNames();
}
