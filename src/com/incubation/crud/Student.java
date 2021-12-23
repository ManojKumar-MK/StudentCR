package com.incubation.crud;

import java.io.Serializable;

public class Student implements Serializable {
    private int rollno;
    private String name;
    private String course;
    private String address;

    public Student(int rollno, String name, String course, String address) {
        this.rollno = rollno;
        this.name = name;
        this.course = course;
        this.address = address;
    }

    public Student() {
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "com.incubation.crud.Student{" +
                "rollno=" + rollno +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
