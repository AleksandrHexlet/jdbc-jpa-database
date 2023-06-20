package ru.itmo.database.entity;

import java.util.ArrayList;

public class Student {
    private int id;
    private String name;
    private Course courses;

    public Student(int id, String name, Course course) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourses() {
        return courses;
    }

    public void setCourses(Course courses) {
        this.courses = courses;
    }
}
