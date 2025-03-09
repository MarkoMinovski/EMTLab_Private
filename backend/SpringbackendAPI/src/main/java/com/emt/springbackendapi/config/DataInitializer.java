package com.emt.springbackendapi.config;

import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.service.AuthorService;
import com.emt.springbackendapi.service.BookService;
import com.emt.springbackendapi.service.CountryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final CountryService countryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public DataInitializer(CountryService countryService, AuthorService authorService, BookService bookService) {
        this.countryService = countryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PostConstruct
    public void init() {
        countryService.create("France", "Europe");
        countryService.create("Japan", "Asia");
        countryService.create("Brazil", "South America");

        authorService.create("Victor", "Hugo", countryService.findById(1L).get());
        authorService.create("Haruki", "Murakami", countryService.findById(2L).get());
        authorService.create("Paulo", "Coelho", countryService.findById(3L).get());

        bookService
                .create("Les Miserables", Category.CLASSICS, 10, authorService.findById(1L).get());
        bookService
                .create("Norwegian Wood", Category.DRAMA, 5, authorService.findById(2L).get());
        bookService
                .create("The Alchemist", Category.FANTASY, 8, authorService.findById(3L).get());
    }
}
