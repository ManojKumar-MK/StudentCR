package com.incubation.crud.service.file;

import com.incubation.crud.Constants;
import com.incubation.crud.Student;
import com.incubation.crud.service.Dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDao implements Dao {
    private ArrayList<Student> students = new ArrayList<>();

    public FileDao(ArrayList<Student> students) {
        this.students = students;
    }

    public FileDao() {
    }

    @Override
    public void add(Student student) {

        students.add(student);

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(Constants.FILENAME_TEXT)));
            objectOutputStream.writeObject(students);
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<Student> list() {

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(Constants.FILENAME_TEXT)));
            students = (ArrayList<Student>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println("Data Retrieved from File ");
        return students;
    }
}
