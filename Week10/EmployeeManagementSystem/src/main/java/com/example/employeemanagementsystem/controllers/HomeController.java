package com.example.employeemanagementsystem.controllers;

import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final EmployeeService employeeService;

    public HomeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("employeeList", employeeService.getAllEmployees());
        model.addAttribute("foundEmployee", null);
        model.addAttribute("searchedId", null);
        model.addAttribute("error", null);
        return "index";
    }

    @GetMapping("/findEmployee")
    public String findEmployee(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("employeeList", employeeService.getAllEmployees());

        if (id != null) {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                model.addAttribute("foundEmployee", List.of(employee));
                model.addAttribute("searchedId", id);
                model.addAttribute("error", null);
            } else {
                model.addAttribute("error", "Employee not found with ID: " + id);
                model.addAttribute("searchedId", id);
                model.addAttribute("foundEmployee", null);
            }
        } else {
            model.addAttribute("foundEmployee", null);
            model.addAttribute("searchedId", null);
            model.addAttribute("error", null);
        }

        return "index";
    }
}