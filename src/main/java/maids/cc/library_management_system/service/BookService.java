package maids.cc.library_management_system.service;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.BookRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    public List<Book> getAll() {
        return bookRepo.findAll();
    }
    //@Cacheable(value = "bookCache",key = "#id")
    public Book getBook(Long id){
        Book book= bookRepo.findById(id).orElseThrow(()->new RuntimeErrorCodedException(ErrorCode.BOOK_NOT_FOUND,"the book is not found"));
        return book;
    }
    public void addBook(Book book){
        if(book.getQuantity()>=book.getBorrowed())
          bookRepo.save(book);
        else
            throw new RuntimeErrorCodedException(ErrorCode.LOGICAL_ERROR,"the borrowed books can't be more than the available quantity");
    }
    //@CachePut(value = "bookCache",key = "#id")
    public void updateBook(Long id,Book newBookDetails){
        Book bookDetails=bookRepo.findById(id).orElseThrow(()->new RuntimeErrorCodedException(ErrorCode.BOOK_NOT_FOUND,"the book is not found"));
        if(newBookDetails.getQuantity()>=newBookDetails.getBorrowed()) {
            bookDetails = Book.builder().id(bookDetails.getId())
                    .title(newBookDetails.getTitle())
                    .ISBN(newBookDetails.getISBN()).quantity(newBookDetails.getQuantity())
                    .publicationDate(newBookDetails.getPublicationDate()).borrowed(newBookDetails.getBorrowed())
                    .author(newBookDetails.getAuthor()).build();
            bookRepo.save(bookDetails);
        }else
            throw new RuntimeErrorCodedException(ErrorCode.LOGICAL_ERROR,"the borrowed books can't be more than the available quantity");
    }
    //@CacheEvict(value = "bookCache",key = "#id")
    public void deleteBook(Long id){
        bookRepo.deleteById(id);
    }
}
