package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.domain.User;
import com.emt.springbackendapi.model.dto.BookDTO;
import com.emt.springbackendapi.model.dto.UpdateAuthorDTO;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.dto.UpdateCountryDTO;
import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.model.exception.NoCopiesAvailableException;
import com.emt.springbackendapi.service.UserService;
import com.emt.springbackendapi.service.application.AuthorApplicationService;
import com.emt.springbackendapi.service.application.BookApplicationService;
import com.emt.springbackendapi.service.application.CountryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "APIs for managing books")
public class BookController {
    private final AuthorApplicationService authorService;
    private final BookApplicationService bookService;
    private final CountryApplicationService countryService;
    private final UserService userService;

    public BookController(AuthorApplicationService authorService, BookApplicationService bookService, CountryApplicationService countryService, UserService userService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.countryService = countryService;
        this.userService = userService;
    }

    @Operation(summary = "Get all books", description = "Fetches all available books.")
    @ApiResponse(responseCode = "200", description = "List of books returned")
    @GetMapping
    private List<UpdateBookDTO> getAll() {
        return this.bookService.findAll();
    }

    @Operation(summary = "Register a new book", description = "Adds a new book to the system.")
    @ApiResponse(responseCode = "200", description = "Book successfully added", content =
    @Content(schema = @Schema(implementation = UpdateBookDTO.class)))
    @PutMapping()
    private ResponseEntity<UpdateBookDTO> registerNewBook(BookDTO bookDTO) {
        UpdateAuthorDTO authorDTO = authorService.findById(bookDTO.getAuthor()).get();
        UpdateCountryDTO countryDTO = this.countryService.findById(authorDTO.country()).get();

        Country countryInternal = countryDTO.toCountry();
        Author authorInternal = authorDTO.toAuthor(countryInternal);

        return this.bookService.create(bookDTO.getName(), bookDTO.getCategory(), bookDTO.getAvailableCopies(),
                authorInternal).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a book", description = "Updates the details of an existing book.")
    @ApiResponse(responseCode = "200", description = "Book successfully updated", content = @Content(schema = @Schema(implementation = UpdateBookDTO.class)))
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

    @Operation(summary = "Delete a book", description = "Removes a book by its ID.")
    @ApiResponse(responseCode = "200", description = "Book successfully deleted")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (this.bookService.findById(id).isPresent()) {
            this.bookService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Mark book as unavailable", description = "Sets a book as unborrowable.")
    @ApiResponse(responseCode = "200", description = "Book marked as unavailable (set copies to 0)")
    @PostMapping("/update/set-unavailable/{id}")
    private ResponseEntity<Void> setUnborrowable(@PathVariable Long id) {
        if (this.bookService.findById(id).isPresent()) {
            this.bookService.setUnborrowable(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Borrow a book (Add to wishlist)", description = "Adds a book to the user's wishlist.")
    @ApiResponse(responseCode = "200", description = "Book added to wishlist", content = @Content(schema =
    @Schema(implementation = UpdateBookDTO.class)))
    @PostMapping("/update/borrow/{id}")
    private ResponseEntity<?> borrowBook(@PathVariable Long id, HttpServletRequest request) throws NoCopiesAvailableException {

        Principal userPrincipal = request.getUserPrincipal();
        Optional<User> currentUser = userService.findByUsername(userPrincipal.getName());
        System.out.println(userPrincipal);
        // questionable

        if (currentUser.isPresent()) {
            Optional<UpdateBookDTO> res =
                    this.bookService.addToWishlist(id, currentUser.get().getId());
            if (res.isPresent()) {
                return res.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
            } else {
                return ResponseEntity.internalServerError().body(Map.of("Internal Reason:",
                        "Book was either unavailable or user attribute doesn't exist"));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Checkout wishlist", description = "Checks out all books in the user's wishlist.")
    @ApiResponse(responseCode = "200", description = "Wishlist successfully checked out",
            content = @Content(schema = @Schema(implementation = UpdateBookDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error occurred (likely because wishlist was empty)")
    @PostMapping("/update/checkout")
    private ResponseEntity<?> checkoutWishlist(HttpServletRequest request) throws NoCopiesAvailableException {
        Principal userPrincipal = request.getUserPrincipal();
        Optional<User> currentUser = userService.findByUsername(userPrincipal.getName());
        if (currentUser.isPresent()) {
            Optional<List<UpdateBookDTO>> resOfOperation = userService.checkoutWishlist(currentUser.get().getUsername());
            if (resOfOperation.isPresent()) {
                return resOfOperation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
            }
        }

        return ResponseEntity.internalServerError().build();
    }

    @Operation(summary = "Get a book by ID", description = "Fetches a book by its ID.")
    @ApiResponse(responseCode = "200", description = "Book found", content = @Content(schema = @Schema(implementation = UpdateBookDTO.class)))
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping("/{id}")
    private ResponseEntity<UpdateBookDTO> getBookById(@PathVariable Long id) {
        return this.bookService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get books by author", description = "Retrieves books written by a specific author.")
    @ApiResponse(responseCode = "200", description = "List of books by author returned")
    @GetMapping("/by-author/{authorId}")
    private List<UpdateBookDTO> getBooksByAuthor(@PathVariable Long authorId) {
        Optional<UpdateAuthorDTO> target = this.authorService.findById(authorId);
        UpdateCountryDTO countryDTO = this.countryService.findById(target.get().country()).get();

        Country countryInternal = countryDTO.toCountry();
        Author targetInternal = target.get().toAuthor(countryInternal);

        return this.bookService.getBooksByAuthor(targetInternal);
    }

    @Operation(summary = "Get books by category", description = "Retrieves books belonging to a specific category.")
    @ApiResponse(responseCode = "200", description = "List of books by category returned")
    @GetMapping("/by-category/{categoryType}")
    private List<UpdateBookDTO> getBooksByCategory(@PathVariable Category categoryType) {
        return this.bookService.getBooksByCategory(categoryType);
    }

}
