package maids.cc.library_management_system.controller;

import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.entity.Patron;
import maids.cc.library_management_system.entity.BorrowingRecord;
import maids.cc.library_management_system.service.BorrowingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BorrowingControllerTest {

    @Mock
    private BorrowingService borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBook() {
        Long bookId = 1L;
        Long patronId = 1L;

        // Act
        ResponseEntity<String> response = borrowingController.borrowBook(bookId, patronId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(borrowingService, times(1)).addBorrowRecord(bookId, patronId);
    }

    @Test
    void testReturnBook() {
        Long bookId = 1L;
        Long patronId = 1L;

        // Act
        ResponseEntity<String> response = borrowingController.returnBook(bookId, patronId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(borrowingService, times(1)).returnBook(bookId, patronId);
    }
}
