package maids.cc.library_management_system.repo;

import maids.cc.library_management_system.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepo extends JpaRepository <Patron, Long> {
}
