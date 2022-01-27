package com.incubation.crud.service.file;

import com.incubation.crud.Constants;
import com.incubation.crud.Student;
import com.incubation.crud.service.Dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDao implements Dao {
    private ArrayList<Student> students = new ArrayList<>();

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

    public void addAll(List<Student> students)
    {
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

    @Override
    public void delete(int rollno) {

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(Constants.FILENAME_TEXT)));
            students = (ArrayList<Student>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean isAvailable = false;
        for(Student s : students)
        {
            if(s.getRollno() == rollno)
            {
                students.remove(s);
                isAvailable = true;
                addAll(students);
                System.out.println("Record deleted from file.");
                break;
            }
        }

        if(!isAvailable)
             System.out.println("No Records in that rollno");


    }

    @Override
    public int get(int rollno) {
        return 0;
    }

    @Override
    public void modify(int rollno, String... params) {

        /*
          Object Fetched from filer.
         */

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(Constants.FILENAME_TEXT)));
            students = (ArrayList<Student>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(Student s : students)
        {
            if(s.getRollno() == rollno)
            {
                s.setAddress(params[0]);
                s.setPhone(params[1]);
                System.out.println("Record modified successfully.");
                addAll(students);  // And Store it in the File

                break;
            }
        }


    }

    @Override
    public Student getRecord(int rollno) {
        return null;
    }
}
