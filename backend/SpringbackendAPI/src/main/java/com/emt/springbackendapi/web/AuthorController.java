package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.Author;
import com.emt.springbackendapi.model.Country;
import com.emt.springbackendapi.model.dto.AuthorDTO;
import com.emt.springbackendapi.service.AuthorService;
import com.emt.springbackendapi.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final CountryService countryService;

    public AuthorController(AuthorService authorService, CountryService countryService) {
        this.authorService = authorService;
        this.countryService = countryService;
    }

    @GetMapping()
    private List<Author> getAuthors() {
        return this.authorService.findAll();
    }

    @PutMapping()
    private ResponseEntity<Author> registerNewAuthor(@RequestBody AuthorDTO authorDTO) {
        Optional<Country> c = this.countryService.findById(authorDTO.getCountry());

        return c.map(country ->
                this.authorService.create(authorDTO.getName(), authorDTO.getSurname(),
                                country).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        Optional<Country> c = this.countryService.findById(authorDTO.getCountry());

        // Looks weird. It looks like it maps the "country" Optional, but it does actually correctly return
        // a ResponseEntity of Author type
        return c.map(country ->
                this.authorService.update(id, authorDTO.getName(), authorDTO.getSurname(),
                                country).map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (authorService.findById(id).isPresent()) {
            authorService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
