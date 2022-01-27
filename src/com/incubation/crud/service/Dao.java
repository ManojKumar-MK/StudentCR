package com.incubation.crud.service;

import com.incubation.crud.Student;

import java.util.List;

public interface Dao {

    public void add(Student student);
    public List<Student> list();
    public void modify(int rollno,String... params);
    public void delete(int rollno);

    public Student getRecord(int rollno);
    public int get(int rollno);
   }
