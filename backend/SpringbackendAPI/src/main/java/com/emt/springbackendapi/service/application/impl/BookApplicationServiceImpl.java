package com.emt.springbackendapi.service.application.impl;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.service.application.BookApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {
    @Override
    public List<UpdateBookDTO> findAll() {
        return List.of();
    }

    @Override
    public Optional<UpdateBookDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UpdateBookDTO> create(String name, Category category, Integer availableCopies, Author a) {
        return Optional.empty();
    }

    @Override
    public Optional<UpdateBookDTO> update(Long id, String name, Category category, Author a) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void setUnborrowable(Long id) {

    }

    @Override
    public Optional<UpdateBookDTO> borrow(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UpdateBookDTO> getBooksByAuthor(Author a) {
        return List.of();
    }

    @Override
    public List<UpdateBookDTO> getBooksByCategory(Category c) {
        return List.of();
    }

    @Override
    public List<UpdateBookDTO> getAvailableBooks() {
        return List.of();
    }
}
