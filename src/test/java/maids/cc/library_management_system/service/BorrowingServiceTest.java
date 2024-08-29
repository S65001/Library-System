package maids.cc.library_management_system.service;

import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.entity.Patron;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.BookRepo;
import maids.cc.library_management_system.repo.BorrowingRepo;
import maids.cc.library_management_system.repo.PatronRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class BorrowingServiceTest {
    @InjectMocks
    private BorrowingService borrowingService;
    @Mock
    private PatronRepo patronRepo;
    @Mock
    private BorrowingRepo borrowingRepo;
    @Mock
    private BookRepo bookRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldAddBorrowRecordSuccessfully(){
        Book book= new Book(1L,"my life","Luther king", LocalDateTime.now(),"017683452",10,3);
        Patron patron= new Patron(1L,"adel","email:loki76@gmail.com , phone number:022345678");
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));

        borrowingService.addBorrowRecord(1L,1L);

    }
    @Test
    public void shouldThrowBookNotFoundInAddingBorrowRecord(){
        Patron patron= new Patron(1L,"adel","email:loki76@gmail.com , phone number:022345678");
        when(bookRepo.findById(2L)).thenThrow(new RuntimeErrorCodedException(ErrorCode.BOOK_NOT_FOUND,"book is not found"));
        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));
        RuntimeErrorCodedException exception=assertThrows(RuntimeErrorCodedException.class,()->borrowingService.addBorrowRecord(2L,1L));
        assertEquals("book is not found",exception.getMessage());
    }
    @Test
    public void shouldThrowPatronNotFoundInAddingBorrowRecord(){
        Book book= new Book(1L,"my life","Luther king", LocalDateTime.now(),"017683452",10,3);
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepo.findById(2L)).thenThrow(new RuntimeErrorCodedException(ErrorCode.PATRON_NOT_FOUND,"patron is not found"));
        RuntimeErrorCodedException exception=assertThrows(RuntimeErrorCodedException.class,()->borrowingService.addBorrowRecord(1L,2L));
        assertEquals("patron is not found",exception.getMessage());

    }
    @Test
    public void shouldThrowLogicalErrorInAddingBorrowRecordDueToQuantityNotAvailable(){
        Book book= new Book(1L,"my life","Luther king", LocalDateTime.now(),"017683452",10,10);
        Patron patron= new Patron(1L,"adel","email:loki76@gmail.com , phone number:022345678");
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));
        RuntimeErrorCodedException exception=assertThrows(RuntimeErrorCodedException.class,()->borrowingService.addBorrowRecord(1L,1L));
        assertEquals("there is no available book",exception.getMessage());
    }

    @Test
    public void shouldThrowNoCopiesFoundInReturnBorrowRecord(){
        Book book= new Book(1L,"my life","Luther king", LocalDateTime.now(),"017683452",10,0);
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        RuntimeErrorCodedException exception=assertThrows(RuntimeErrorCodedException.class,()->borrowingService.returnBook(1L,1L));
        assertEquals("there are no copies of the book currently borrowed",exception.getMessage());

    }
    @Test
    public void shouldRecordNotFoundInReturnBorrowRecordS(){
        Book book= new Book(1L,"my life","Luther king", LocalDateTime.now(),"017683452",10,3);
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(borrowingRepo.findTop1ByBookIdAndPatronIdAndReturnDateIsNullOrderByBorrowDateAsc(1L,2L)).thenThrow(new RuntimeErrorCodedException(ErrorCode.RECORD_NOT_FOUND,"No active borrowing record found for book ID " + 1 + " and patron ID " + 2 + ". This may occur if the book was never borrowed or has already been returned."));
        RuntimeErrorCodedException exception=assertThrows(RuntimeErrorCodedException.class,()->borrowingService.returnBook(1L,2L));
        assertEquals("No active borrowing record found for book ID " + 1 + " and patron ID " + 2 + ". This may occur if the book was never borrowed or has already been returned.",exception.getMessage());

    }
}
