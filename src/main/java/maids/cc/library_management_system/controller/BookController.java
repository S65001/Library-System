package maids.cc.library_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.exception.ErrorDetails;
import maids.cc.library_management_system.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "API for managing books in the library")
@SecurityRequirement(name = "bearerAuth")
public class BookController {
    private final BookService bookService;
    @Operation(summary = "Get all books", description = "Retrieve a list of all books available in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping()

    public List<Book> getAllBooks(){
        return bookService.getAll();
    }


    @Operation(summary = "Get a book by ID", description = "Retrieve detailed information about a specific book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved book details"),
            @ApiResponse(responseCode = "400", description = "Invalid book ID provided or Book not found", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id){
        Book book=bookService.getBook(id);
        return ResponseEntity.ok(book);
    }


    @Operation(summary = "Add a new book", description = "Add a new book to the library's catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully added"),
            @ApiResponse(responseCode = "400", description = "Invalid book data provided", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book){
        bookService.addBook(book);
        return ResponseEntity.ok("success");
    }
    @Operation(summary = "Update an existing book", description = "Update the details of an existing book in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid book data or ID provided or Book not found", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id,@RequestBody Book book){
        bookService.updateBook(id ,book);
        return ResponseEntity.ok("success");
    }
    @Operation(summary = "Delete a book", description = "Delete a book from the library's catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid book ID provided or Book not found", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("success");
    }
}
