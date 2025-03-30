package com.emt.springbackendapi.service.application;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Book;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.enums.Category;

import java.util.List;
import java.util.Optional;

public interface BookApplicationService {
    List<UpdateBookDTO> findAll();
    Optional<UpdateBookDTO> findById(Long id);
    Optional<UpdateBookDTO> create(String name, Category category, Integer availableCopies, Author a);
    Optional<UpdateBookDTO> update(Long id, String name, Category category, Author a);
    void delete(Long id);
    void setUnborrowable(Long id);
    Optional<UpdateBookDTO> borrow(Long id);

    List<UpdateBookDTO> getBooksByAuthor(Author a);
    List<UpdateBookDTO> getBooksByCategory(Category c);
    List<UpdateBookDTO> getAvailableBooks();
}
