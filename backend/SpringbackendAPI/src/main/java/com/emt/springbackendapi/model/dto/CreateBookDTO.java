package com.emt.springbackendapi.model.dto;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Book;
import com.emt.springbackendapi.model.enums.Category;

import java.util.List;

public record CreateBookDTO(
        String name,
        Category category,
        Long author,
        int availableCopies
) {

    public static CreateBookDTO from(Book book) {
        return new CreateBookDTO(
                book.getName(),
                book.getCategory(),
                book.getAuthor().getId(),
                book.getAvailableCopies()
        );
    }

    public static List<CreateBookDTO> fromList(List<Book> books) {
        return books.stream().map(CreateBookDTO::from).toList();
    }

    public Book toBook(Author author) {
        return new Book(
                this.name,
                this.category,
                this.availableCopies,
                author
        );
    }


}
