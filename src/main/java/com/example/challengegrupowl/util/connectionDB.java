package com.example.challengegrupowl.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class connectionDB {

    public static Connection getConnection() {

        try {
            final String connection = getProperties().getProperty("spring.datasource.url");
            final String user = getProperties().getProperty("spring.datasource.username");
            final String password = getProperties().getProperty("spring.datasource.password");

            return DriverManager.getConnection(connection, user, password);
        } catch(SQLException | IOException e) {

            throw new RuntimeException(e);
        }
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();

        String path = "/application.properties";
        properties.load(connectionDB.class.getResourceAsStream(path));
        return properties;
    }
}
