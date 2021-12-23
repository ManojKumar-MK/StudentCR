package com.incubation.crud.service;

import com.incubation.crud.Student;

import java.util.List;

public interface Dao {

    public void add(Student student);
    public List<Student> list();
}
