package com.incubation.crud.service.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private String url = "jdbc:mysql://localhost:3306/ems";
    private String username = "root";
    private String password = "root";

    private static ConnectionFactory connectionFactory = null;

    private ConnectionFactory()
    {
        // LOAD AND REGISTER THE DRIVER CLASS
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws SQLException {

        Connection connection = null;
        connection = DriverManager.getConnection(url,username,password);
        return connection;
    }

    public static ConnectionFactory connectionFactory()
    {
        if(connectionFactory==null)
        {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;

    }
}
