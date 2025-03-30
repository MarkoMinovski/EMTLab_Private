package com.emt.springbackendapi.service.application.impl;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Book;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.repository.BookRepository;
import com.emt.springbackendapi.service.application.BookApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {

    private final BookRepository bookRepository;

    public BookApplicationServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public List<UpdateBookDTO> findAll() {
        return UpdateBookDTO.fromList(bookRepository.findAll());
    }

    @Override
    public Optional<UpdateBookDTO> findById(Long id) {
        return Optional.of(UpdateBookDTO.from(bookRepository.findById(id).get()));
    }

    @Override
    public Optional<UpdateBookDTO> create(String name, Category category, Integer availableCopies, Author a) {
        Book internal = bookRepository.save(new Book(name, category, availableCopies, a));
        return Optional.of(UpdateBookDTO.from(internal));
    }

    @Override
    public Optional<UpdateBookDTO> update(Long id, String name, Category category, Author a) {
        Book target = bookRepository.findById(id).get();

        target.setName(name);
        target.setCategory(category);
        target.setAuthor(a);

        target = bookRepository.save(target);

        return Optional.of(UpdateBookDTO.from(target));
    }

    @Override
    public void delete(Long id) {
        Book target = bookRepository.findById(id).get();
        bookRepository.delete(target);
    }

    @Override
    public void setUnborrowable(Long id) {
        Book target = bookRepository.findById(id).get();
        target.setAvailableCopies(0);
    }

    @Override
    public Optional<UpdateBookDTO> borrow(Long id) {
        Book target = bookRepository.findById(id).get();

        if (target.getAvailableCopies() > 0) {
            target.setAvailableCopies(target.getAvailableCopies() - 1);
            target = bookRepository.save(target);
        }


        return Optional.of(UpdateBookDTO.from(target));
    }

    @Override
    public List<UpdateBookDTO> getBooksByAuthor(Author a) {
        List<Book> listResBookInternal = bookRepository.findBooksByAuthor_Id(a.getId());
        return UpdateBookDTO.fromList(listResBookInternal);
    }

    @Override
    public List<UpdateBookDTO> getBooksByCategory(Category c) {
        List<Book> listResBookInternal = bookRepository.findBooksByCategory(c);
        return UpdateBookDTO.fromList(listResBookInternal);
    }

    @Override
    public List<UpdateBookDTO> getAvailableBooks() {
        List<Book> listResBookInternal = bookRepository.findBookByAvailableCopiesGreaterThan(0);
        return UpdateBookDTO.fromList(listResBookInternal);
    }
}
