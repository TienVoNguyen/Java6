package com.example.demostreamapi.models;

public class Student {
    private String name;
    private Boolean gender;
    private double mark;

    public Student() {
    }

    public Student(String name, Boolean gender, double mark) {
        this.name = name;
        this.gender = gender;
        this.mark = mark;
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

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }
}
