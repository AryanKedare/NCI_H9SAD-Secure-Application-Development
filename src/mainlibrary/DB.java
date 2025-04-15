/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database utility class for managing connections to the MySQL database.
 */
public class DB {

    // Database credentials
    public static String user = "root";
    public static String connection = "jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false";

    /**
     * Retrieves a connection to the database.
     * 
     * @return Connection object or null if connection fails.
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            Properties props = new Properties();
            props.put("user", user);

            // Use environment variables or a secure configuration file for the password in production
            props.put("password", "Aryan123@");
            props.put("useUnicode", "true");
            props.put("useServerPrepStmts", "false"); // Use client-side prepared statements
            props.put("characterEncoding", "UTF-8"); // Ensure charset is UTF-8

            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            con = DriverManager.getConnection(connection, props);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Ensure it's added to the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database. Check the connection URL, username, or password.");
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Safely closes the database connection.
     * 
     * @param con The Connection object to close.
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection.");
                e.printStackTrace();
            }
        }
    }
}
