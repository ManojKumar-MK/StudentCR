package com.incubation.crud.service.db;

import com.incubation.crud.Student;
import com.incubation.crud.service.Dao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class DBDao implements Dao {

    private Connection connection;
    private Statement statement ;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Connection getConnection()
    {
        Connection connection = null;
        try {
            connection = ConnectionFactory.connectionFactory().getConnection();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(connection.hashCode());
        return connection;
    }
    @Override
    public void add(Student student) {
        connection =  getConnection();

        try {
            String query = "INSERT INTO student VALUES(?,?,?,?,?,?)"; // ONE FOR AUTO INCREMENT
            // I can use INSERT INTO student (rollno,name,course,address,phone) values (?,?,?,?,?);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, NULL);
            preparedStatement.setInt(2, student.getRollno());
            preparedStatement.setString(3, student.getName());
            preparedStatement.setString(4, student.getCourse());
            preparedStatement.setString(5, student.getAddress());
            preparedStatement.setString(6, student.getPhone());

            int rows = preparedStatement.executeUpdate();

            System.out.println(rows + "  Inserted in DB");


            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    @Override
    public List<Student> list() {
        ArrayList<Student> studentArrayList = new ArrayList<>();
        connection = getConnection();
        String query = "SELECT * FROM student";

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int rollno = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String course = resultSet.getString(4);
                String address = resultSet.getString(5);
                String phone = resultSet.getString(6);

                studentArrayList.add(new Student(rollno, name, course, address,phone));
            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Data Retrieved from DB");
        return studentArrayList;
    }

    @Override
    public void delete(int rollno) {

        connection = getConnection();
        String query = "DELETE FROM student WHERE rollno = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rollno);
            int rows = preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.close();
            System.out.println(rows + " Record Deleted from DB..");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Student getRecord(int rollno) {
        connection = getConnection();
        Student student = null;
        try {


            String query = "SELECT * FROM student rollno = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rollno);
            ResultSet resultSet = preparedStatement.executeQuery();

            int rno = Integer.parseInt(resultSet.getString("rollno"));
            String name  = resultSet.getString("name");
            String course = resultSet.getString("course");
            String address = resultSet.getString("address");
            String phone = resultSet.getString("address");
            student = new Student(rno,name,course,address,phone);


            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;

    }

    @Override
    public int get(int rollno) {

        int rows = 0;

        connection = getConnection();
        String query  = "SELECT * FROM student WHERE rollno = "+rollno;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.last();
            rows = resultSet.getRow();
            resultSet.beforeFirst();

            System.out.println(rows);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    @Override
    public void modify(int rollno, String... params) {

        connection = getConnection();
        try {


            String query = "UPDATE student SET address = ? , phone = ? WHERE rollno = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setInt(3, rollno);

            int rows = preparedStatement.executeUpdate();
            System.out.println(rows +" record modified ");


            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
