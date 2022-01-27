package com.incubation.crud;

import com.incubation.crud.service.Dao;
import com.incubation.crud.service.db.DBDao;
import com.incubation.crud.service.file.FileDao;
import com.incubation.crud.service.list.ArrayListDao;
import com.incubation.crud.service.xml.XmlDao;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApplication {


    static Scanner scanner=new Scanner(System.in);


    static ArrayList students = null;
    static Dao arrayListDao = null;
    static Dao fileDao = null;
    static Dao xmlDao = null;
    static Dao dbDao = null;



    static  // Block before Instance block
    {
        xmlDao = new XmlDao();
        fileDao = new FileDao();
        dbDao = new DBDao();

        /*
           Retrieve Record from DB Bcoz ArrayList values not there after exceution.
         */
        students = (ArrayList) dbDao.list();
        arrayListDao = new ArrayListDao(students);

        //System.out.println(students);
    }


    public static void main(String[] args) {

        int mainChoice;
        do {
            System.out.println("----- MAIN MENU ----");
            System.out.println("1. Add");
            System.out.println("2. List");
            System.out.println("3. Modify");
            System.out.println("4. Delete");
            System.out.println("5. Exit");

            System.out.println("Enter the Choice :");
            mainChoice = scanner.nextInt();scanner.nextLine();

            switch (mainChoice){

                case 1: addRecord();
                    break;

                case 2:  listRecords();
                    break;

                case 3:  modifyRecords();
                    break;

                case 4:  deleteRecord();
                    break;

                case 5:
                    System.out.println("Thank You For Using.");
                    break;

                default:
                    System.out.println("Invalid Option.");



            }


        }while (mainChoice!=5);



    }

    private static void modifyRecords() {

        System.out.println("Enter the rollno : ");
        int rollno = scanner.nextInt();scanner.nextLine();
        if(dbDao.get(rollno) > 0)  // If record available modify.
        {
            System.out.println("Record there...");

            System.out.println("Enter the address to modify on rollno : "+rollno);

            String address = scanner.nextLine();

            System.out.println("Enter the phone to modify on rollno : "+rollno);

            String phone = scanner.nextLine();

            arrayListDao.modify(rollno,address,phone);
            fileDao.modify(rollno,address,phone);
            xmlDao.modify(rollno,address,phone);
            dbDao.modify(rollno,address,phone);




        }
        else {
            System.out.println("No Reords available in that rollno.");
        }

    }

    private static void deleteRecord() {

        System.out.println("Enter the rollno :");
        int rollno = scanner.nextInt();scanner.nextLine();

            xmlDao.delete(rollno);
            fileDao.delete(rollno);

            xmlDao.delete(rollno);
            dbDao.delete(rollno);


    }

    private static void listRecords() {

     //   ArrayList<com.incubation.crud.Student> students = (ArrayList<com.incubation.crud.Student>) arrayListDao.list();
        ArrayList<Student> students = (ArrayList<Student>) xmlDao.list();

        if(students.isEmpty()) {
            System.out.println("No Student Records.");
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


        System.out.println("Enter the phone :");
        String phone = scanner.nextLine();
        student.setPhone(phone);

        arrayListDao.add(student);
        fileDao.add(student);
        xmlDao.add(student);
        dbDao.add(student);


    }
}

