package com.incubation.crud.service.db;

import com.incubation.crud.Student;
import com.incubation.crud.service.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class DBDao implements Dao {

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private Connection getConnection()
    {
        Connection connection = null;
        try {
            connection =  ConnectionFactory.connectionFactory().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public void add(Student student) {
        connection =  getConnection();

        try {
            String query = "INSERT INTO student VALUES(?,?,?,?,?)"; // ONE FOR AUTO INCREMENT
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, NULL);
            preparedStatement.setInt(2, student.getRollno());
            preparedStatement.setString(3, student.getName());
            preparedStatement.setString(4, student.getCourse());
            preparedStatement.setString(5, student.getAddress());

            int rows = preparedStatement.executeUpdate();

            System.out.println(rows + "Inserted in DB");


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

                studentArrayList.add(new Student(rollno, name, course, address));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Data Retrieved from DB");
        return studentArrayList;
    }
}
