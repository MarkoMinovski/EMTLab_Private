package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.AuthorsPerCountryMView;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.AuthorDTO;
import com.emt.springbackendapi.model.dto.UpdateAuthorDTO;
import com.emt.springbackendapi.model.dto.UpdateCountryDTO;
import com.emt.springbackendapi.service.AuthorsPerCountryService;
import com.emt.springbackendapi.service.CountryService;
import com.emt.springbackendapi.service.application.AuthorApplicationService;
import com.emt.springbackendapi.service.application.CountryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author Controller", description = "APIs for managing authors")
public class AuthorController {

    private final AuthorApplicationService authorService;
    private final CountryApplicationService countryApplicationService;
    private final CountryService countryDomainService;
    private final AuthorsPerCountryService authorsPerCountryService;

    public AuthorController(AuthorApplicationService authorService, CountryApplicationService countryApplicationService, CountryService countryDomainService, AuthorsPerCountryService authorsPerCountryService) {
        this.authorService = authorService;
        this.countryApplicationService = countryApplicationService;
        this.countryDomainService = countryDomainService;
        this.authorsPerCountryService = authorsPerCountryService;
    }

    @Operation(summary = "Get all authors", description = "Retrieves a list of all authors.")
    @ApiResponse(responseCode = "200", description = "List of authors returned")
    @GetMapping()
    private List<UpdateAuthorDTO> getAuthors() {
        return this.authorService.findAll();
    }

    @Operation(summary = "Register a new author", description = "Creates a new author with the provided details.")
    @ApiResponse(responseCode = "200", description = "Author successfully created", content =
    @Content(schema = @Schema(implementation = UpdateAuthorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Author not found")
    @PutMapping()
    private ResponseEntity<UpdateAuthorDTO> registerNewAuthor(@RequestBody AuthorDTO authorDTO) {
        Optional<Country> c = this.countryDomainService.findById(authorDTO.getCountry());
        Optional<UpdateAuthorDTO> updateAuthorDTOOptional
                = this.authorService.create(authorDTO.getName(), authorDTO.getSurname(), c.get());

        return updateAuthorDTOOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing author", description = "Updates the details of an author by ID.")
    @ApiResponse(responseCode = "200", description = "Author successfully updated", content =
    @Content(schema = @Schema(implementation = UpdateAuthorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Author not found")
    @PostMapping("/update/{id}")
    private ResponseEntity<UpdateAuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        Optional<Country> c = this.countryDomainService.findById(authorDTO.getCountry());
        Optional<UpdateAuthorDTO> updateAuthorDTOOptional = this.authorService.update(id, authorDTO.getName(),
                authorDTO.getSurname(), c.get());

        return updateAuthorDTOOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an author", description = "Deletes an author by ID.")
    @ApiResponse(responseCode = "200", description = "Author successfully deleted")
    @ApiResponse(responseCode = "404", description = "Author not found")
    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (authorService.findById(id).isPresent()) {
            authorService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Retrieve number of authors per country", description = "Given a country, returns the number" +
            "of authors in the system from it. Uses a materialized view, so may be slightly out of date (query twice" +
            "to guarantee result)")
    @ApiResponse(responseCode = "200", description = "Country and numOfAuthors returned")
    @ApiResponse(responseCode = "404", description = "Country not found")
    @ApiResponse(responseCode = "401", description = "Sent bad request: multiple request parameters, etc...")
    @GetMapping("/by-country")
    private ResponseEntity<AuthorsPerCountryMView> getAuthorsByCountry(@RequestParam String countryName) {
        Optional<AuthorsPerCountryMView> queryResult =
                authorsPerCountryService.FindAuthorsPerCountryByCountryName(countryName);

        if (queryResult.isPresent()) {
            return queryResult.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.badRequest().build();
    }

}
