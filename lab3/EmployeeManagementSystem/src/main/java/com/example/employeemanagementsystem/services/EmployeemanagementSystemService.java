package com.example.employeemanagementsystem.services;

import com.example.employeemanagementsystem.models.Employee;
import jakarta.ejb.Stateless;
import java.util.*;

@Stateless
public class EmployeemanagementSystemService {

    private static List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1, "John", "Doe", "john@email.com"));
        employees.add(new Employee(2, "Jane", "Smith", "jane@email.com"));
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee getById(int id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Employee create(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee update(int id, Employee employee) {
        Employee existing = getById(id);
        if (existing == null) return null;

        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        return existing;
    }

    public boolean delete(int id) {
        return employees.removeIf(e -> e.getId() == id);
    }
}
