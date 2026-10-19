package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<Employee> findAll(String keyword) {
        return (keyword == null || keyword.isBlank()) ? repo.findAll() : repo.search(keyword.trim());
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Employee " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public boolean emailTaken(String email, Long ownId) {
        return repo.findByEmail(email).map(e -> !e.getId().equals(ownId)).orElse(false);
    }

    public Employee save(Employee e) { return repo.save(e); }

    public void delete(Long id) { repo.deleteById(id); }
}
