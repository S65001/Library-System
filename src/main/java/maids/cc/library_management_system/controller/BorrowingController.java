package maids.cc.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.service.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BorrowingController {
    private final BorrowingService borrowingRecordService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable("bookId") Long bookId, @PathVariable("patronId") Long patronId) {
        borrowingRecordService.addBorrowRecord(bookId, patronId);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok("success");
    }
}
