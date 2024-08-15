package maids.cc.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.service.BookService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id){
        return ResponseEntity.ok(bookService.getBook(id));
    }
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book){
        bookService.addBook(book);
        return ResponseEntity.ok("success");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id,@RequestBody Book book){
        bookService.updateBook(id ,book);
        return ResponseEntity.ok("success");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("success");
    }
}
