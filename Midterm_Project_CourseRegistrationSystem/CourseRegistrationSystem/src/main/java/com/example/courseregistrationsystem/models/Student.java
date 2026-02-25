package com.example.courseregistrationsystem.models;

import java.util.HashSet;
import java.util.Set;

public class Student {
    private int id;
    private String first_name;
    private String last_name;
    private String email;

    private Set<Integer> enrolled_course_ids = new HashSet<>();

    public Student() {}

    public Student(int id, String first_name, String last_name, String email) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return first_name; }
    public void setFirstName(String first_name) { this.first_name = first_name; }

    public String getLastName() { return last_name; }
    public void setLastName(String last_name) { this.last_name = last_name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<Integer> getEnrolledCourseIds() { return enrolled_course_ids; }
    public void setEnrolledCourseIds(Set<Integer> enrolled_course_ids) {
        this.enrolled_course_ids = enrolled_course_ids;
    }
}
