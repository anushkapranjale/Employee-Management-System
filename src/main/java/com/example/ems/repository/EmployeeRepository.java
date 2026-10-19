package com.example.ems.repository;

import com.example.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("""
           SELECT e FROM Employee e
           WHERE LOWER(e.firstName)   LIKE LOWER(CONCAT('%', :kw, '%'))
              OR LOWER(e.lastName)    LIKE LOWER(CONCAT('%', :kw, '%'))
              OR LOWER(e.department)  LIKE LOWER(CONCAT('%', :kw, '%'))
              OR LOWER(e.designation) LIKE LOWER(CONCAT('%', :kw, '%'))
           ORDER BY e.firstName
           """)
    List<Employee> search(@Param("kw") String keyword);
}
