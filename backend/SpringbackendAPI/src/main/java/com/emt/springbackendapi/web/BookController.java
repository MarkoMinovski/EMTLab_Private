package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.domain.Author;
import com.emt.springbackendapi.model.domain.BooksPerAuthorMView;
import com.emt.springbackendapi.model.domain.Country;
import com.emt.springbackendapi.model.domain.User;
import com.emt.springbackendapi.model.dto.BookDTO;
import com.emt.springbackendapi.model.dto.UpdateAuthorDTO;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.dto.UpdateCountryDTO;
import com.emt.springbackendapi.model.enums.Category;
import com.emt.springbackendapi.model.exception.NoCopiesAvailableException;
import com.emt.springbackendapi.service.AuthorService;
import com.emt.springbackendapi.service.BooksPerAuthorService;
import com.emt.springbackendapi.service.CountryService;
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
@Tag(name = "Book Controller", description = "Endpoints for Book CRUD, filtering and more.")
public class BookController {
    private final AuthorService authorDomainService;
    private final BookApplicationService bookService;
    private final UserService userService;

    public BookController(AuthorService authorDomainService, BookApplicationService bookService,
                          UserService userService) {
        this.authorDomainService = authorDomainService;
        this.bookService = bookService;
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
        Optional<Author> authorOfBookToBeRegistered = this.authorDomainService.findById(bookDTO.getAuthor());
        if (authorOfBookToBeRegistered.isPresent()) {
            Optional<UpdateBookDTO> optionalUpdateBookDTOReturnValue = this.bookService.
                    create(bookDTO.getName(), bookDTO.getCategory(), bookDTO.getAvailableCopies(),
                    authorOfBookToBeRegistered.get());

            if (optionalUpdateBookDTOReturnValue.isPresent()) {
                return optionalUpdateBookDTOReturnValue.map(ResponseEntity::ok).
                        orElse(ResponseEntity.unprocessableEntity().build());
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update a book", description = "Updates the details of an existing book.")
    @ApiResponse(responseCode = "200", description = "Book successfully updated", content = @Content(schema = @Schema(implementation = UpdateBookDTO.class)))
    @PostMapping("/update/{id}")
    private ResponseEntity<UpdateBookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Optional<Author> newAuthorValue = this.authorDomainService.findById(bookDTO.getAuthor());

        if (newAuthorValue.isPresent()) {
            Optional<UpdateBookDTO> updateBookDTOOptionalReturnValue = this.bookService.update(id,
                    bookDTO.getName(), bookDTO.getCategory(), newAuthorValue.get());

            return updateBookDTOOptionalReturnValue.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.unprocessableEntity().build());
        }

        return ResponseEntity.badRequest().build();
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

    /*@Operation(summary = "Get number of books by author", description = "Retrieves number of " +
            "books written by a specific author.")
    @ApiResponse(responseCode = "200", description = "Number of books by author returned")
    @GetMapping("/by-author")
    private ResponseEntity<BooksPerAuthorMView> getBooksByAuthor(@RequestParam Long authorId) {
        Optional<BooksPerAuthorMView> queryResult = this
                .booksPerAuthorService.findBooksPerAuthorRecordByAuthorId(authorId);

        if (queryResult.isPresent()) {
            return queryResult.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/by-author/all")
    private List<BooksPerAuthorMView> getBooksPerAuthorAll() {
        return this.booksPerAuthorService.findAll();
    }

    @Operation(summary = "Get books by category", description = "Retrieves books belonging to a specific category.")
    @ApiResponse(responseCode = "200", description = "List of books by category returned")
    @GetMapping("/by-category/{categoryType}")
    private List<UpdateBookDTO> getBooksByCategory(@PathVariable Category categoryType) {
        return this.bookService.getBooksByCategory(categoryType);
    }*/

}
