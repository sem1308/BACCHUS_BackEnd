package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.Employee;
import uos.seclass.bacchus.domains.Member;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    public Optional<Employee> findById(String id);
}
