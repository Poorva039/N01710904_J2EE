package com.example.courseregistrationsystem.services;

import com.example.courseregistrationsystem.models.Course;
import jakarta.ejb.Stateless;

import java.util.*;

@Stateless
public class CourseService {
    private static final Map<Integer, Course> courseMap = new HashMap<>();
    private static int currentId = 1;

    public List<Course> getAll() {
        return new ArrayList<>(courseMap.values());
    }

    public Course getById(int id) {
        return courseMap.get(id);
    }

    public Course create(Course c) {
        c.setId(currentId++);
        courseMap.put(c.getId(), c);
        return c;
    }

    public Course update(int id, Course c) {
        if (!courseMap.containsKey(id)) return null;
        c.setId(id);
        courseMap.put(id, c);
        return c;
    }

    public boolean delete(int id) {
        return courseMap.remove(id) != null;
    }
}