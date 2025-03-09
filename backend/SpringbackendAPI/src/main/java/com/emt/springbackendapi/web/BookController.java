package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.Book;
import com.emt.springbackendapi.model.dto.BookDTO;
import com.emt.springbackendapi.service.AuthorService;
import com.emt.springbackendapi.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private ResponseEntity<Book> updateBook(@PathVariable Long id, BookDTO bookDTO) {
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
}
