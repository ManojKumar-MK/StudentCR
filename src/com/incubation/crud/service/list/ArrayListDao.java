package com.incubation.crud.service.list;

import com.incubation.crud.Student;
import com.incubation.crud.service.Dao;


import java.util.ArrayList;
import java.util.List;

public class ArrayListDao implements Dao {

    private ArrayList<Student> students = new ArrayList<>();

    public ArrayListDao()
    {

    }
    public ArrayListDao(ArrayList<Student> students) {

        this.students = students;
    }

    @Override
    public void add(Student student) {
        students.add(student);
        System.out.println("Data Added Successfully in list");
    }

    @Override
    public List<Student> list() {

        if(!students.isEmpty())
            System.out.println("Data Retrieved from ArrayList");
        return students;
    }

    @Override
    public void delete(int rollno) {

        boolean isAvailable = false;
        for(Student s : students)
        {
            if(s.getRollno() == rollno)
            {
                isAvailable = true;
                students.remove(s);
                System.out.println("Record removed from list");
                break;
            }
        }

        if(!isAvailable)
            System.out.println("No Records in that rollno");




    }

    @Override
    public Student getRecord(int rollno) {

        for(Student s : students)
        {
            if(s.getRollno() == rollno)
            {
                return s;
            }
        }
        return null;
    }

    @Override
    public int get(int rollno) {
        return 0;
    }

    @Override
    public void modify(int rollno,String... params) {

        Student s = null;

        s = getRecord(rollno);

        for(Student student : students)
        {

            if(student.equals(s))
            {
                System.out.println("Student hashcode :"+student.hashCode());

                System.out.println("S hashcode :"+s.hashCode());

                student.setAddress(params[0]);
                student.setPhone(params[1]);

                  System.out.println("Record modified successfully.");

                break;
            }
        }

    }
}
