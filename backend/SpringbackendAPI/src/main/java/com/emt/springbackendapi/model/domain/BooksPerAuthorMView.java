package com.emt.springbackendapi.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.context.annotation.Profile;


@Data
@Entity
@Subselect("SELECT * FROM public.books_per_author")
@Immutable
@Profile("psql")
public class BooksPerAuthorMView {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "num_of_books")
    private Long numberOfBooks;
}
