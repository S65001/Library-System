package maids.cc.library_management_system.repo;

import maids.cc.library_management_system.entity.Book;
import maids.cc.library_management_system.entity.BorrowingRecord;
import maids.cc.library_management_system.entity.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;


import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class BorrowingRepoTest {
    @Autowired
    private BorrowingRepo borrowingRepo;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private PatronRepo patronRepo;

    BorrowingRecord record1;
    BorrowingRecord record2;
    BorrowingRecord record3;
    BorrowingRecord record4;

    @BeforeEach
    void setUp() {
        Book book1=new Book(1L,"my life","Luther king", LocalDateTime.now(),"017683452",10,3);
        Book book2=new Book(2L,"new book","Franz Kafka", LocalDateTime.now(),"017683902",20,8);
        Patron patron1= new Patron(1L,"adel","email:loki76@gmail.com , phone number:022345678");
        Patron patron2= new Patron(2L,"maged","email:maged@gmail.com , phone number:022589807");
        record1=new BorrowingRecord(null,book1,patron1, LocalDate.now(),LocalDate.now());
        record2=new BorrowingRecord(null,book2,patron1, LocalDate.now(),null);
        record3=new BorrowingRecord(null,book2,patron2, LocalDate.now(),null);
        record4=new BorrowingRecord(null,book1,patron2, LocalDate.now(),null);
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(patron1);
        entityManager.persist(patron2);
//        entityManager.merge(record1);
//        entityManager.merge(record2);
//        entityManager.merge(record3);
    }
    @Test
    public void shouldSaveBorrowingRecord(){
        BorrowingRecord borrowingRecord=borrowingRepo.save(record4);
        assertThat(borrowingRecord).isEqualTo(record4);
    }

}