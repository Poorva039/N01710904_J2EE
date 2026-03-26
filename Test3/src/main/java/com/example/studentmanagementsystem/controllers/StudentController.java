package com.example.studentmanagementsystem.controllers;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Display all records page
    @GetMapping("/")
    public String viewAllStudents(Model model) {
        model.addAttribute("studentList", studentService.getAllStudents());
        return "index";
    }

    // Show form to add new record
    @GetMapping("/showAddStudentForm")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "addStudent";
    }

    // Process form submission to add new record
    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("student") Student student) {
        studentService.addStudent(student);
        return "redirect:/";
    }

    // Show form to update record
    @GetMapping("/showUpdateForm/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "updateStudent";
    }

    // Process form submission to update record
    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute("student") Student student) {
        studentService.updateStudent(student);
        return "redirect:/";
    }

    // Delete record
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return "redirect:/";
    }
}