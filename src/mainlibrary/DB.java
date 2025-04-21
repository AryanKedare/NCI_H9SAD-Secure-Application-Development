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
            props.put("password", "Aryan123@");
            props.put("useUnicode", "true");
            props.put("useServerPrepStmts", "false");
            props.put("characterEncoding", "UTF-8");

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(connection, props);
        } catch (ClassNotFoundException | SQLException e) {
            logError("Database connection error.", e);
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
                logError("Failed to close the database connection.", e);
            }
        }
    }

    /**
     * Logs errors in a secure way.
     * 
     * @param message The error message to log.
     * @param e       The exception to log.
     */
    private static void logError(String message, Exception e) {
        // Dynamically set debug mode based on an environment variable or system property
        boolean isDebugMode = Boolean.parseBoolean(System.getProperty("DEBUG_MODE", "false")); // Reads DEBUG_MODE system property, defaults to "false"

        if (isDebugMode) {
            System.err.println(message);
            e.printStackTrace();
        } else {
            // Log the error securely (e.g., to a file or monitoring system)
            // Example: Logger.error(message, e);
        }
    }
}
