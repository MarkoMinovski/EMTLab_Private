package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Book;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.BookDTO;
import com.emt.springbackendapi.model.dto.UpdateAuthorDTO;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.dto.UpdateCountryDTO;
import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.service.application.AuthorApplicationService;
import com.emt.springbackendapi.service.application.BookApplicationService;
import com.emt.springbackendapi.service.application.CountryApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final AuthorApplicationService authorService;
    private final BookApplicationService bookService;
    private final CountryApplicationService countryService;

    public BookController(AuthorApplicationService authorService, BookApplicationService bookService, CountryApplicationService countryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.countryService = countryService;
    }

    @GetMapping
    private List<UpdateBookDTO> getAll() {
        return this.bookService.findAll();
    }

    @PutMapping()
    private ResponseEntity<UpdateBookDTO> registerNewBook(BookDTO bookDTO) {
        UpdateAuthorDTO authorDTO = authorService.findById(bookDTO.getAuthor()).get();
        UpdateCountryDTO countryDTO = this.countryService.findById(authorDTO.country()).get();

        Country countryInternal = countryDTO.toCountry();
        Author authorInternal = authorDTO.toAuthor(countryInternal);

        return this.bookService.create(bookDTO.getName(), bookDTO.getCategory(), bookDTO.getAvailableCopies(),
                authorInternal).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<UpdateBookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        UpdateAuthorDTO targetDTO = this.authorService.findById(bookDTO.getAuthor()).get();
        UpdateCountryDTO countryDTO = this.countryService.findById(targetDTO.country()).get();

        Country countryInternal = countryDTO.toCountry();
        Author targetAuthor = targetDTO.toAuthor(countryInternal);

        return this.bookService
                .update(id, bookDTO.getName(), bookDTO.getCategory(), targetAuthor).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (this.bookService.findById(id).isPresent()) {
            this.bookService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update/set-unavailable/{id}")
    private ResponseEntity<Void> setUnborrowable(@PathVariable Long id) {
        if (this.bookService.findById(id).isPresent()) {
            this.bookService.setUnborrowable(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update/borrow/{id}")
    private ResponseEntity<UpdateBookDTO> borrowBook(@PathVariable Long id) {
        return this.bookService.borrow(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    private ResponseEntity<UpdateBookDTO> getBookById(@PathVariable Long id) {
        return this.bookService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-author/{authorId}")
    private List<UpdateBookDTO> getBooksByAuthor(@PathVariable Long authorId) {
        Optional<UpdateAuthorDTO> target = this.authorService.findById(authorId);
        UpdateCountryDTO countryDTO = this.countryService.findById(target.get().country()).get();

        Country countryInternal = countryDTO.toCountry();
        Author targetInternal = target.get().toAuthor(countryInternal);

        return this.bookService.getBooksByAuthor(targetInternal);
    }

    @GetMapping("/by-category/{categoryType}")
    private List<UpdateBookDTO> getBooksByCategory(@PathVariable Category categoryType) {
        return this.bookService.getBooksByCategory(categoryType);
    }

}
