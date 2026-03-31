package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final List<Employee> employeeList = new ArrayList<>();

    public List<Employee> findAll() {
        return employeeList;
    }

    public void save(Employee employee) {
        employeeList.add(employee);
    }

    public Employee findById(Long id) {
        for (Employee employee : employeeList) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    public void update(Employee updatedEmployee) {
        for (Employee employee : employeeList) {
            if (employee.getId().equals(updatedEmployee.getId())) {
                employee.setFirstName(updatedEmployee.getFirstName());
                employee.setLastName(updatedEmployee.getLastName());
                employee.setEmail(updatedEmployee.getEmail());
                break;
            }
        }
    }

    public void deleteById(Long id) {
        employeeList.removeIf(employee -> employee.getId().equals(id));
    }
}