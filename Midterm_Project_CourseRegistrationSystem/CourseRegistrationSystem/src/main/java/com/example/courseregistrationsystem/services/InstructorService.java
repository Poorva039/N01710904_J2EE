package com.example.courseregistrationsystem.services;

import com.example.courseregistrationsystem.models.Instructor;
import jakarta.ejb.Stateless;

import java.util.*;

@Stateless
public class InstructorService {
    private static final Map<Integer, Instructor> instructorMap = new HashMap<>();
    private static int currentId = 1;

    public List<Instructor> getAll() {
        return new ArrayList<>(instructorMap.values());
    }

    public Instructor getById(int id) {
        return instructorMap.get(id);
    }

    public Instructor create(Instructor i) {
        i.setId(currentId++);
        instructorMap.put(i.getId(), i);
        return i;
    }

    public Instructor update(int id, Instructor i) {
        if (!instructorMap.containsKey(id)) return null;
        i.setId(id);
        instructorMap.put(id, i);
        return i;
    }

    public boolean delete(int id) {
        return instructorMap.remove(id) != null;
    }
}