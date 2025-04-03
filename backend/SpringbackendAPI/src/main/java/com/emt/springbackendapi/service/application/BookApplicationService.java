package com.emt.springbackendapi.service.application;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.model.exception.NoCopiesAvailableException;

import java.util.List;
import java.util.Optional;

public interface BookApplicationService {
    List<UpdateBookDTO> findAll();
    Optional<UpdateBookDTO> findById(Long id);
    Optional<UpdateBookDTO> create(String name, Category category, Integer availableCopies, Author a);
    Optional<UpdateBookDTO> update(Long id, String name, Category category, Author a);
    void delete(Long id);
    void setUnborrowable(Long id);
    Optional<UpdateBookDTO> addToWishlist(Long bookID, Long userID) throws NoCopiesAvailableException;
    Optional<UpdateBookDTO> borrow(Long bookID) throws NoCopiesAvailableException;
    List<UpdateBookDTO> getBooksByAuthor(Author a);
    List<UpdateBookDTO> getBooksByCategory(Category c);
    List<UpdateBookDTO> getAvailableBooks();
}
