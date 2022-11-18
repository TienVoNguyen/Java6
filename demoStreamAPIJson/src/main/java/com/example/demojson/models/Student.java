package com.example.demojson.models;

import java.util.List;

public class Student {
    private String name;
    private Boolean gender;
    private double marks;
    private Contact contact;
    private List<String> subjects;

    public Student() {
    }

    public Student(String name, Boolean gender, double marks, Contact contact, List<String> subjects) {
        this.name = name;
        this.gender = gender;
        this.marks = marks;
        this.contact = contact;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}
