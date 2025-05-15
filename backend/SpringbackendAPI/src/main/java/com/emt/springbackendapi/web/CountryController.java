package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.dto.CreateCountryDTO;
import com.emt.springbackendapi.model.dto.UpdateCountryDTO;
import com.emt.springbackendapi.service.application.CountryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/country")
@Tag(name = "Country Controller", description = "Endpoints for managing Country CRUD, filtering, and more.")
public class CountryController {

    private final CountryApplicationService countryService;

    public CountryController(CountryApplicationService countryService) {
        this.countryService = countryService;
    }

    @Operation(summary = "Get countries", description = "Fetch countries based on name or continent.")
    @ApiResponse(responseCode = "200", description = "List of matching countries returned")
    @GetMapping()
    public List<UpdateCountryDTO> getCountries(@RequestParam(required = false)
                                                   @Parameter(description = "Partial or full name of the country")
                                                   String nameString,
                                               @RequestParam(required = false)
                                               @Parameter(description = "Exact name of the continent")
                                               String continentString) {

        if (nameString != null && continentString != null) {
            return new ArrayList<>();
        }

        if (nameString != null) {
            return this.countryService.findByNameContainingString(nameString);
        } else if (continentString != null) {
            return this.countryService.findByContinent(continentString);
        }

        return this.countryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UpdateCountryDTO> getCountry(@PathVariable Long id) {
        return this.countryService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Add a country", description = "Creates a new country entry.")
    @ApiResponse(responseCode = "200", description = "Country successfully added",
            content = @Content(schema = @Schema(implementation = UpdateCountryDTO.class)))
    @PutMapping("/add")
    public ResponseEntity<UpdateCountryDTO> addCountry(@RequestBody CreateCountryDTO countryDTO) {
        System.out.print(countryDTO);
        return this.countryService.create(countryDTO.name(), countryDTO.continent()).map(ResponseEntity::ok).orElse(ResponseEntity.ok().build());
    }

    @Operation(summary = "Update a country", description = "Updates the details of an existing country.")
    @ApiResponse(responseCode = "200", description = "Country successfully updated",
            content = @Content(schema = @Schema(implementation = UpdateCountryDTO.class)))
    @PostMapping("/update/{id}")
    public ResponseEntity<UpdateCountryDTO> updateCountry(String name, String continent, @PathVariable Long id) {
        return this.countryService.update(id, name, continent).map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }

    @Operation(summary = "Delete a country", description = "Deletes a country by its ID.")
    @ApiResponse(responseCode = "200", description = "Country successfully deleted")
    @ApiResponse(responseCode = "404", description = "Country not found")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        Optional<UpdateCountryDTO> c = this.countryService.findById(id);

        if (c.isPresent()) {
            this.countryService.delete(id);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


}
