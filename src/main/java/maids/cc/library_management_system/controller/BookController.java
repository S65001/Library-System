package maids.cc.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping()
    public List<Book> getAllBooks(){
        return bookService.getAll();
    }
    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") Long id){
        return bookService.getBook(id);
    }
    @PostMapping
    public void addBook(@RequestBody Book book){
        bookService.addBook(book);
    }
    @PutMapping("/{id}")
    public void updateBook(@PathVariable("id") Long id,@RequestBody Book book){
        bookService.updateBook(id ,book);
    }
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
    }
}
