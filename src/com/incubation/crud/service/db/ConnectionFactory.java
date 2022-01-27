package com.incubation.crud.service.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private Connection connection = null;
    private String url = "jdbc:mysql://localhost:3306/ems";
    private String username = "root";
    private String password = "root";

    private static ConnectionFactory connectionFactory = null;

    private ConnectionFactory()
    {
        // LOAD AND REGISTER THE DRIVER CLASS
        try {

            Properties properties = new Properties();
            properties.load(new FileReader("dbconfig.properties"));
            Class.forName(properties.getProperty("db.driver"));

        } catch (ClassNotFoundException  | IOException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws SQLException, IOException {
        if(connection == null || connection.isClosed()) {
            Properties p = new Properties();
            try {
                p.load(new FileReader("dbconfig.properties"));
                connection = DriverManager.getConnection(p.getProperty("db.url"), p.getProperty("db.username"), p.getProperty("db.password"));
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

        }
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
