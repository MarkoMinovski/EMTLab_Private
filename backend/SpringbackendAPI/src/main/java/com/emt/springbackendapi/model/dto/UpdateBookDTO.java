package com.emt.springbackendapi.model.dto;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Book;
import com.emt.springbackendapi.model.enums.Category;

import java.util.List;

public record UpdateBookDTO(
        Long id,
        String name,
        Category category,
        Long author,
        int availableCopies
) {

    public static UpdateBookDTO from(Book book) {
        return new UpdateBookDTO(
                book.getId(),
                book.getName(),
                book.getCategory(),
                book.getAuthor().getId(),
                book.getAvailableCopies()
        );
    }

    public static List<UpdateBookDTO> fromList(List<Book> books) {
        return books.stream().map(UpdateBookDTO::from).toList();
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

