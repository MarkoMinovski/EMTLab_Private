package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.AuthorDTO;
import com.emt.springbackendapi.model.dto.UpdateAuthorDTO;
import com.emt.springbackendapi.model.dto.UpdateCountryDTO;
import com.emt.springbackendapi.service.CountryService;
import com.emt.springbackendapi.service.application.AuthorApplicationService;
import com.emt.springbackendapi.service.application.CountryApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorApplicationService authorService;
    private final CountryApplicationService countryService;

    public AuthorController(AuthorApplicationService authorService, CountryApplicationService countryService) {
        this.authorService = authorService;
        this.countryService = countryService;
    }

    @GetMapping()
    private List<UpdateAuthorDTO> getAuthors() {
        return this.authorService.findAll();
    }

    @PutMapping()
    private ResponseEntity<UpdateAuthorDTO> registerNewAuthor(@RequestBody AuthorDTO authorDTO) {
        Optional<UpdateCountryDTO> c = this.countryService.findById(authorDTO.getCountry());

        return c.map(country ->
                this.authorService.create(authorDTO.getName(), authorDTO.getSurname(),
                                country.toCountry()).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<UpdateAuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        Optional<UpdateCountryDTO> c = this.countryService.findById(authorDTO.getCountry());

        // Looks weird. It looks like it maps the "country" Optional, but it does actually correctly return
        // a ResponseEntity of Author type
        return c.map(country ->
                this.authorService.update(id, authorDTO.getName(), authorDTO.getSurname(),
                                country.toCountry()).map(ResponseEntity::ok)
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
