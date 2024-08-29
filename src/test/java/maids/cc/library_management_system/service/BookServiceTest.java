package maids.cc.library_management_system.service;

import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class BookServiceTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepo bookRepo;
    private  List<Book> bookList;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookList = new ArrayList<>();
        bookList.add(new Book(1L,"my life","Luther king", LocalDateTime.now(),"017683452",10,3));
        bookList.add(new Book(null,"army","Anne", LocalDateTime.now(),"017683454",30,50));
        bookList.add(new Book(null,"art of war","Author", LocalDateTime.now(),"017683476",20,7));
    }
    @Test
    public void should_getAllBooks_Successfully(){
        //given

        //mocking by adding values to dependencies
        Mockito.when(bookService.getAll()).thenReturn(bookList);

        //when I call getALl()
        List<Book> returnedBooks=bookService.getAll();

        //then assert and verify
        assertEquals(bookList.size(),returnedBooks.size());
        verify(bookRepo,times(1)).findAll();

    }
    @Test
    public void should_getBookById_Successfully(){
        //given
        Book book= bookList.get(0);
        //mock
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        //when
        Book returnedBook=bookService.getBook(1L);
        // then
        assertEquals(book.getId(),returnedBook.getId());
        assertEquals(book.getTitle(),returnedBook.getTitle());
        assertEquals(book.getAuthor(),returnedBook.getAuthor());
        assertEquals(book.getISBN(),returnedBook.getISBN());
        assertEquals(book.getPublicationDate(),returnedBook.getPublicationDate());
        assertEquals(book.getQuantity(),returnedBook.getQuantity());
        assertEquals(book.getBorrowed(),returnedBook.getBorrowed());
        verify(bookRepo,times(1)).findById(1L);
    }
    @Test
    public void should_getBookById_Unsuccessfully(){
        var message=assertThrows(RuntimeErrorCodedException.class,()->bookService.getBook(2L));
        assertEquals("the book is not found",message.getMessage());
    }
    @Test
    public void shouldThrowLogicalErrorInAddingBook(){
        //given
        Book book=bookList.get(1);

        when(bookRepo.save(any(Book.class))).thenThrow(new RuntimeErrorCodedException(ErrorCode.LOGICAL_ERROR,"the borrowed books can't be more than the available quantity"));

        // when & then
        RuntimeErrorCodedException thrownException = assertThrows(RuntimeErrorCodedException.class, () -> bookService.addBook(book));

        assertEquals("the borrowed books can't be more than the available quantity", thrownException.getMessage());

    }
    @Test
    public void should_UpdateBook_Successfully(){
        //given
        Book book= bookList.get(0);
        Book newBook=bookList.get(2);
        //mock
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepo.save(any(Book.class))).then(invocationOnMock -> {
           newBook.setId(book.getId());
           return newBook;
        });
        //when
        Book returnedBook=bookService.updateBook(1L,newBook);
        // then
        assertEquals(book.getId(),returnedBook.getId());
        assertEquals(newBook.getTitle(),returnedBook.getTitle());
        assertEquals(newBook.getAuthor(),returnedBook.getAuthor());
        assertEquals(newBook.getISBN(),returnedBook.getISBN());
        assertEquals(newBook.getPublicationDate(),returnedBook.getPublicationDate());
        assertEquals(newBook.getQuantity(),returnedBook.getQuantity());
        assertEquals(newBook.getBorrowed(),returnedBook.getBorrowed());
        verify(bookRepo,times(1)).findById(1L);
    }
    @Test
    public void shouldThrowLogicErrorOnUpdateBook(){
        //given
        Book book= bookList.get(0);
        Book newBook=bookList.get(1);
        //mock
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepo.save(any(Book.class))).then(invocationOnMock -> {
            newBook.setId(book.getId());
            return newBook;
        });
        //when & then
        RuntimeErrorCodedException exception= assertThrows(RuntimeErrorCodedException.class,()->bookService.updateBook(1L,newBook));

        assertEquals("the borrowed books can't be more than the available quantity",exception.getMessage());
        verify(bookRepo,times(1)).findById(1L);
        verify(bookRepo,times(0)).save(any(Book.class));
    }


}