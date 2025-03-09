package com.emt.springbackendapi.model.dto;

import com.emt.springbackendapi.model.enums.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {
    private String name;
    private Category category;
    private Long author;
    private int availableCopies;

    public BookDTO(String name, Category category, Long author, int availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }
}
