package com.emt.springbackendapi.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @ManyToOne
    private Country countryOfOrigin;

    public Author(String name, String surname, Country countryOfOrigin) {
        this.name = name;
        this.surname = surname;
        this.countryOfOrigin = countryOfOrigin;
    }
}
