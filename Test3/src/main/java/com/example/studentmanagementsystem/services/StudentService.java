package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entity.Student;
import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> getAllStudents();
    void addStudent(Student student);
    Student getStudentById(String id);
    void updateStudent(Student student);
    void deleteStudent(String id);
    Map<String, Student> getStudentMap();
}