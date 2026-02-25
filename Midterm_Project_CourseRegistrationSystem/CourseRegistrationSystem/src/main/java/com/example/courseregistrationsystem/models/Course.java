package com.example.courseregistrationsystem.models;

public class Course {
    private int id;
    private String code;
    private String title;
    private int credit_hours;
    private int instructor_id;

    public Course() {}

    public Course(int id, String code, String title, int credit_hours, int instructor_id) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.credit_hours = credit_hours;
        this.instructor_id = instructor_id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCreditHours() { return credit_hours; }
    public void setCreditHours(int credit_hours) { this.credit_hours = credit_hours; }

    public int getInstructorId() { return instructor_id; }
    public void setInstructorId(int instructor_id) { this.instructor_id = instructor_id; }
}
