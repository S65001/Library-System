package maids.cc.library_management_system.controller;


import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAll()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertEquals(2, result.size());
        verify(bookService, times(1)).getAll();
    }

    @Test
    void testGetBook() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        when(bookService.getBook(bookId)).thenReturn(book);

        ResponseEntity<Book> response = bookController.getBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookId, response.getBody().getId());
        verify(bookService, times(1)).getBook(bookId);
    }

    @Test
    void testAddBook() {
        Book book = new Book();

        ResponseEntity<String> response = bookController.addBook(book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(bookService, times(1)).addBook(book);
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        Book book = new Book();

        ResponseEntity<String> response = bookController.updateBook(bookId, book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(bookService, times(1)).updateBook(bookId, book);
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;

        ResponseEntity<String> response = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(bookService, times(1)).deleteBook(bookId);
    }
}
