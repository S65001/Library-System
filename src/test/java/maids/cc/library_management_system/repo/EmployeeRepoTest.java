package maids.cc.library_management_system.repo;

import maids.cc.library_management_system.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmployeeRepoTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private EmployeeRepo employeeRepo;
    Employee employee;
    Employee employee2;
    Employee employee3;
    @BeforeEach
    void setUp() {
         employee= new Employee(null,"mostafa","mahmoud","mm@gmail.com","password","023819417",true);
         employee2= new Employee(null,"ahmed","mahmoud","am@gmail.com","password","023819433",true);
         employee3= new Employee(null,"reda","mahmoud","rm@gmail.com","password","023839452",true);
         entityManager.persist(employee);
         entityManager.persist(employee2);

    }
    @Test
    public void shouldFindEmployeeByEmailSuccessfully(){
        Optional<Employee> foundEmployee=employeeRepo.findEmployeeByEmail("mm@gmail.com");
        assertEquals(1L,foundEmployee.get().getId());
        assertEquals(employee.getEmail(),foundEmployee.get().getEmail());
        assertEquals(employee.getFirstname(),foundEmployee.get().getFirstname());
        assertEquals(employee.getLastname(),foundEmployee.get().getLastname());
        assertEquals(employee.getPhoneNumber(),foundEmployee.get().getPhoneNumber());
        assertEquals(employee.getPassword(),foundEmployee.get().getPassword());
        assertEquals(employee.getIsEnabled(),foundEmployee.get().getIsEnabled());
    }
    @Test
    public void shouldReturnEmptyInEmailNotFound(){
        Optional <Employee> e=employeeRepo.findEmployeeByEmail("en@gmail.com");
        assertThat(e).isEmpty();
    }
    @Test
    public void shouldSaveEmployeeSuccessfully(){
        Employee emp=employeeRepo.save(employee3);
        assertThat(emp).isEqualTo(employee3);
    }
}