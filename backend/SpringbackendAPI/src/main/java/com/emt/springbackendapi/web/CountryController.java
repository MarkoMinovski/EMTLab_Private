package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.Country;
import com.emt.springbackendapi.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Gets countries from the database that match the specified filters, or all of them if none are provided.
     * Please do not provide both request parameters! There are no two countries with the same name
     * on Earth on different continents. I mean logically it makes no sense. If you do, the method returns empty.
     * @param nameString the string containing the name of the country you are requesting.
     * @param continentString the string equalling the name of the continent you are requesting. Note that
     *                        unlike the nameString parameter, the continentString must match exactly (I mean,
     *                        it makes no sense to allow substring searching for continents)
     *
     * @return List containing the result - or empty if both request parameters are provided.
     */
    @GetMapping()
    public List<Country> getCountries(@RequestParam(required = false) String nameString,
                                      @RequestParam(required = false) String continentString) {

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

    @PutMapping("/add")
    public ResponseEntity<Country> addCountry(String name, String continent) {
        return this.countryService.create(name, continent).map(ResponseEntity::ok).orElse(ResponseEntity.ok().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Country> updateCountry(String name, String continent, @PathVariable Long id) {
        return this.countryService.update(id, name, continent).map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        Optional<Country> c = this.countryService.findById(id);

        if (c.isPresent()) {
            this.countryService.delete(id);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }




}
