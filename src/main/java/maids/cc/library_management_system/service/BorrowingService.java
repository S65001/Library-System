package maids.cc.library_management_system.service;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.entity.BorrowingRecord;
import maids.cc.library_management_system.entity.Patron;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.BookRepo;
import maids.cc.library_management_system.repo.BorrowingRepo;
import maids.cc.library_management_system.repo.PatronRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BorrowingService {
    private final BorrowingRepo borrowingRepo;
    private final PatronRepo patronRepo;
    private final BookRepo bookRepo;
    @Transactional
    public void addBorrowRecord(Long bookId, Long patronId){
        Book book =bookRepo.findById(bookId).orElseThrow(()-> new RuntimeErrorCodedException(ErrorCode.BOOK_NOT_FOUND,"book is not found"));
        Patron patron=patronRepo.findById(patronId).orElseThrow(()->new RuntimeErrorCodedException(ErrorCode.PATRON_NOT_FOUND,"patron is not found"));
        BorrowingRecord record= BorrowingRecord.builder()
                 .book(book)
                 .patron(patron)
                 .borrowDate(LocalDate.now())
                 .build();
        if(book.getQuantity()>book.getBorrowed()){
            book.setBorrowed(book.getBorrowed()+1);
            bookRepo.save(book);
            borrowingRepo.save(record);
        }else{
           throw new RuntimeErrorCodedException(ErrorCode.LOGICAL_ERROR,"there is no available book");
        }

    }
    @Transactional
    public void returnBook(Long bookId, Long patronId){
        Book book =bookRepo.findById(bookId).orElseThrow(()-> new RuntimeErrorCodedException(ErrorCode.BOOK_NOT_FOUND,"book is not found"));
        if(book.getBorrowed()< 1) throw new RuntimeErrorCodedException(ErrorCode.LOGICAL_ERROR,"there no borrowed books with that name");
        book.setBorrowed(book.getBorrowed()-1);
        bookRepo.save(book);
        BorrowingRecord record=borrowingRepo.findTop1ByBookIdAndPatronIdAndReturnDateIsNullOrderByBorrowDateAsc(bookId,patronId);
        record.setReturnDate(LocalDate.now());
        borrowingRepo.save(record);
    }
}
