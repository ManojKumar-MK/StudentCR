package com.incubation.crud.service.list;

import com.incubation.crud.Student;
import com.incubation.crud.service.Dao;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDao implements Dao {

    private ArrayList<Student> students = new ArrayList<>();

    @Override
    public void add(Student student) {
        students.add(student);
        System.out.println("Data Added Successfully in list");
    }

    @Override
    public List<Student> list() {


        System.out.println("Data Retrieved from ArrayList");
        return students;
    }
}
