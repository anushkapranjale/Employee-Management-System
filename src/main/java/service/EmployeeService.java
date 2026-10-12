package com.ems.employee_management_system.service;

import com.ems.employee_management_system.entity.Employee;
import com.ems.employee_management_system.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + id));
    }

    public Employee deactivateEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employee.setActive(false);
        return employeeRepository.save(employee);
    }


    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());

        return employeeRepository.save(existingEmployee);
    }

}

