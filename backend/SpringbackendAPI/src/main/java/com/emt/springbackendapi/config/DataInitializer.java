package com.emt.springbackendapi.config;

import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.model.enums.Role;
import com.emt.springbackendapi.service.AuthorService;
import com.emt.springbackendapi.service.BookService;
import com.emt.springbackendapi.service.CountryService;
import com.emt.springbackendapi.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final CountryService countryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final UserService userService;

    public DataInitializer(CountryService countryService, AuthorService authorService, BookService bookService, UserService userService) {
        this.countryService = countryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {

        if (countryService.findAll().isEmpty()) {
            countryService.create("France", "Europe");
            countryService.create("Japan", "Asia");
            countryService.create("Brazil", "South America");
            countryService.create("USA", "North America");
            countryService.create("Germany", "Europe");
            countryService.create("India", "Asia");
        }


        if (authorService.findAll().isEmpty()) {
            authorService.create("Victor", "Hugo", countryService.findById(1L).get());
            authorService.create("Haruki", "Murakami", countryService.findById(2L).get());
            authorService.create("Paulo", "Coelho", countryService.findById(3L).get());
            authorService.create("Ernest", "Hemingway", countryService.findById(4L).get());
            authorService.create("Johann Wolfgang", "von Goethe", countryService.findById(5L).get());
            authorService.create("R.K.", "Narayan", countryService.findById(6L).get());
            authorService.create("Albert", "Camus", countryService.findById(1L).get());

        }


        if (bookService.findAll().isEmpty()) {
            bookService.create("Les Misérables", Category.CLASSICS, 10, authorService.findById(1L).get());
            bookService.create("Norwegian Wood", Category.DRAMA, 5, authorService.findById(2L).get());
            bookService.create("The Alchemist", Category.FANTASY, 8, authorService.findById(3L).get());
            bookService.create("The Old Man and the Sea", Category.THRILLER, 7, authorService.findById(4L).get());
            bookService.create("Faust", Category.HISTORICAL, 6, authorService.findById(5L).get());
            bookService.create("Malgudi Days", Category.BIOGRAPHY, 4, authorService.findById(6L).get());
            bookService.create("L'etranger", Category.CLASSICS, 9, authorService.findById(7L).get());
        }

        if (userService.findByUsername("admin").isEmpty()) {
            userService.register("admin", "abcd", "abcd", Role.ROLE_LIBRARIAN);
        }
    }

}
