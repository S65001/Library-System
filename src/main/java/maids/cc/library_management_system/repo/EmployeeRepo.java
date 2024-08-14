package maids.cc.library_management_system.repo;

import maids.cc.library_management_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepo extends JpaRepository<Employee,Long> {
}
