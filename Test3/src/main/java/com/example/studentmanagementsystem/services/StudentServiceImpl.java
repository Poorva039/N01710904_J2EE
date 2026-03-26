package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entity.Student;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<String, Student> studentMap = new HashMap<>();

    public StudentServiceImpl() {
        initializeData();
    }

    private void initializeData() {
        Student student1 = new Student(
                "1",
                "Poorva Patel",
                20,
                "Female",
                "poorva@gmail.com",
                "Toronto",
                LocalDate.of(2004, 5, 15)
        );

        // Default Student 2
        Student student2 = new Student(
                "2",
                "Shachi Patel",
                22,
                "Female",
                "shachi@gmail.com",
                "Vancouver",
                LocalDate.of(2002, 8, 22)
        );

        // Default Student 3
        Student student3 = new Student(
                "3",
                "Aarv Agnihotri",
                21,
                "Male",
                "aarv@gmail.com",
                "Montreal",
                LocalDate.of(2003, 3, 10)
        );

        studentMap.put(student1.getId(), student1);
        studentMap.put(student2.getId(), student2);
        studentMap.put(student3.getId(), student3);
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentMap.values());
    }

    @Override
    public void addStudent(Student student) {
        studentMap.put(student.getId(), student);
    }

    @Override
    public Student getStudentById(String id) {
        return studentMap.get(id);
    }

    @Override
    public void updateStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
        }
    }

    @Override
    public void deleteStudent(String id) {
        studentMap.remove(id);
    }

    @Override
    public Map<String, Student> getStudentMap() {
        return studentMap;
    }
}