package com.emt.springbackendapi.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Immutable
@Data
@Entity
@Subselect("SELECT * FROM public.authors_per_country")
public class AuthorsPerCountryMView {

    @Id
    @Column(name = "name")
    private String countryName;

    @Column(name = "num_of_authors")
    private Long numOfAuthors;
}
