package com.emt.springbackendapi.model;

import com.emt.springbackendapi.model.enums.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Category category;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Author author;

    private int availableCopies;

    public Book(String name, Category category, int availableCopies, Author author) {
        this.name = name;
        this.category = category;
        this.availableCopies = availableCopies;
        this.author = author;
    }

}
