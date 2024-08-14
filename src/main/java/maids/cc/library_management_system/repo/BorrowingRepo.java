package maids.cc.library_management_system.repo;

import maids.cc.library_management_system.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepo extends JpaRepository<BorrowingRecord, Long> {
    BorrowingRecord findTop1ByBookIdAndPatronIdAndReturnDateIsNullOrderByBorrowDateAsc(Long bookId, Long patronId);
}
