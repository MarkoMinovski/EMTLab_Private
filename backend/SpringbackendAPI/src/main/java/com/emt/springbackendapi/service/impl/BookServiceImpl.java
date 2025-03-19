package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.Author;
import com.emt.springbackendapi.model.Book;
import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.repository.AuthorRepository;
import com.emt.springbackendapi.repository.BookRepository;
import com.emt.springbackendapi.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> create(String name, Category category, Integer availableCopies, Author a) {
        return Optional.of(this.bookRepository.save(new Book(name, category, availableCopies, a)));
    }

    @Override
    public Optional<Book> update(Long id, String name, Category category, Author a) {
        Book b = this.bookRepository.findById(id).orElse(null);

        if (b != null) {
            b.setName(name);
            b.setCategory(category);
            b.setAuthor(a);

            return Optional.of(this.bookRepository.save(b));
        } else {
            System.out.println("Book that needed updating not found!");
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        this.bookRepository.delete(this.bookRepository.findById(id).get());
    }

    @Override
    public void setUnborrowable(Long id) {
        Book b = this.bookRepository.findById(id).get();
        b.setAvailableCopies(0);
        this.bookRepository.save(b);
    }

    @Override
    public Optional<Book> borrow(Long id) {
        Book b = this.bookRepository.findById(id).orElse(null);

        if (b != null) {
            if (b.getAvailableCopies() > 0) {
                b.setAvailableCopies(b.getAvailableCopies() - 1);
            }

            return Optional.of(this.bookRepository.save(b));
        } else {
            System.out.println("Book that needed updating not found!");
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getBooksByAuthor(Author a) {
        Optional<Author> target = this.authorRepository.findById(a.getId());

        if (target.isPresent()) {
            return this.bookRepository.findBooksByAuthor_Id(target.get().getId());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Book> getBooksByCategory(Category c) {
        return this.bookRepository.findBooksByCategory(c);
    }

    @Override
    public List<Book> getAvailableBooks() {
        return this.bookRepository.findBookByAvailableCopiesGreaterThan(0);
    }

}
