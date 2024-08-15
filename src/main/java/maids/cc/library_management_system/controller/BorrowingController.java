package maids.cc.library_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.service.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Borrowing", description = "Operations related to borrowing and returning books")
@SecurityRequirement(name = "bearerAuth")
public class BorrowingController {
    private final BorrowingService borrowingRecordService;
    @Operation(summary = "Borrow a book",
            description = "Allows a patron to borrow a book from the library",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully borrowed the book"),
                    @ApiResponse(responseCode = "400", description = "Invalid request or book not available")
            })
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable("bookId") @Parameter(description = "ID of the book to be borrowed") Long bookId,
                                             @PathVariable("patronId") @Parameter(description = "ID of the patron borrowing the book") Long patronId) {
        borrowingRecordService.addBorrowRecord(bookId, patronId);
        return ResponseEntity.ok("success");
    }
    @Operation(summary = "Return a borrowed book",
            description = "Allows a patron to return a book that was previously borrowed",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned the book"),
                    @ApiResponse(responseCode = "400", description = "Invalid request or no active borrowing record found")
            })
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok("success");
    }
}
