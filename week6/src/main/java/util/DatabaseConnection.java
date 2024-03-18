package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/hoteldb";
    private static final String USER = "postgres_username";
    private static final String PASS = "astrongpass";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}