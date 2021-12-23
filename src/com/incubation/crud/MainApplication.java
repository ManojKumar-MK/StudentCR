package com.incubation.crud;

import com.incubation.crud.service.Dao;
import com.incubation.crud.service.db.DBDao;
import com.incubation.crud.service.file.FileDao;
import com.incubation.crud.service.list.ArrayListDao;
import com.incubation.crud.service.xml.XmlDao;

import java.util.ArrayList;
import java.util.Scanner;

public class MainApplication {

    static Scanner scanner=new Scanner(System.in);

    static Dao arrayListDao = new ArrayListDao();   // Interface
    static Dao fileDao = new FileDao();      // So It can hold Reference
    static Dao xmlDao = new XmlDao();
    static Dao dbDao = new DBDao();


    public static void main(String[] args) {

        int mainChoice;
        do {
            System.out.println("----- MAIN MENU ----");
            System.out.println("1. Add");
            System.out.println("2. List");
            System.out.println("3. Exit");

            System.out.println("Enter the Choice :");
            mainChoice = scanner.nextInt();scanner.nextLine();

            switch (mainChoice){

                case 1: addRecord();
                    break;

                case 2:  listRecords();
                    break;

                case 3:
                    System.out.println("Thank You For Using.");
                    break;

                default:
                    System.out.println("Invalid Option.");


            }


        }while (mainChoice!=3);



    }

    private static void listRecords() {

     //   ArrayList<com.incubation.crud.Student> students = (ArrayList<com.incubation.crud.Student>) arrayListDao.list();
        ArrayList<Student> students = (ArrayList<Student>) dbDao.list();

        if(students.isEmpty()) {
            System.out.println("No com.incubation.crud.Student Records.");
        }
        else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
     }

    private static void addRecord() {

        Student student = new Student();

        System.out.println("Enter the rollno :");
        int rollno = scanner.nextInt();scanner.nextLine();
        student.setRollno(rollno);

        System.out.println("Enter the name :");
        String name =  scanner.nextLine();
        student.setName(name);

        System.out.println("Enter the course :");
        String course = scanner.nextLine();
        student.setCourse(course);


        System.out.println("Enter the address :");
        String address = scanner.nextLine();
        student.setAddress(address);


        //arrayListDao.add(student);
        //fileDao.add(student);
        dbDao.add(student);
    }
}

