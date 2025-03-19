package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.Author;
import com.emt.springbackendapi.model.Book;
import com.emt.springbackendapi.model.dto.BookDTO;
import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.service.AuthorService;
import com.emt.springbackendapi.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final AuthorService authorService;
    private final BookService bookService;

    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    private List<Book> getAll() {
        return this.bookService.findAll();
    }

    @PutMapping()
    private ResponseEntity<Book> registerNewBook(BookDTO bookDTO) {
        return this.bookService.create(bookDTO.getName(), bookDTO.getCategory(), bookDTO.getAvailableCopies(),
                this.authorService.findById(bookDTO.getAuthor()).get()).
                map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return this.bookService.update(id, bookDTO.getName(), bookDTO.getCategory(),
                this.authorService.findById(bookDTO.getAuthor()).get()).map(ResponseEntity::ok)
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
    private ResponseEntity<Book> borrowBook(@PathVariable Long id) {
        return this.bookService.borrow(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return this.bookService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-author/{authorId}")
    private List<Book> getBooksByAuthor(@PathVariable Long authorId) {
        Optional<Author> target = this.authorService.findById(authorId);

        if (target.isPresent()) {
            return this.bookService.getBooksByAuthor(target.get());
        }

        return new ArrayList<>();
    }

    @GetMapping("/by-category/{categoryType}")
    private List<Book> getBooksByCategory(@PathVariable Category categoryType) {
        return this.bookService.getBooksByCategory(categoryType);
    }

}
