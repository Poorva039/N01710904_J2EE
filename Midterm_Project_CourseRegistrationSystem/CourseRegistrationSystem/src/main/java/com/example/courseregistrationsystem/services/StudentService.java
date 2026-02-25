package com.example.courseregistrationsystem.services;

import com.example.courseregistrationsystem.models.Student;
import jakarta.ejb.Stateless;

import java.util.*;

@Stateless
public class StudentService {
    private static final Map<Integer, Student> studentMap = new HashMap<>();
    private static int currentId = 1;

    public List<Student> getAll() {
        return new ArrayList<>(studentMap.values());
    }

    public Student getById(int id) {
        return studentMap.get(id);
    }

    public Student create(Student s) {
        s.setId(currentId++);
        studentMap.put(s.getId(), s);
        return s;
    }

    public Student update(int id, Student s) {
        if (!studentMap.containsKey(id)) return null;
        s.setId(id);
        if (s.getEnrolledCourseIds() == null) {
            s.setEnrolledCourseIds(studentMap.get(id).getEnrolledCourseIds());
        }
        studentMap.put(id, s);
        return s;
    }

    public boolean delete(int id) {
        return studentMap.remove(id) != null;
    }

    public boolean enroll(int studentId, int courseId) {
        Student s = studentMap.get(studentId);
        if (s == null) return false;
        return s.getEnrolledCourseIds().add(courseId);
    }

    public boolean drop(int studentId, int courseId) {
        Student s = studentMap.get(studentId);
        if (s == null) return false;
        return s.getEnrolledCourseIds().remove(courseId);
    }
}