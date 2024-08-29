package maids.cc.library_management_system.repo;

import maids.cc.library_management_system.entity.Employee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    @Cacheable(value = "users",key = "#email")
    Optional<Employee> findEmployeeByEmail(String email);
}
