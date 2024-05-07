package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConnexionDb {
    // Configuration properties for MySQL
    private static final String DB_CONNECTION = "mysql";
    private static final String DB_HOST = "127.0.0.1";
    private static final String DB_PORT = "3306";
    private static final String DB_DATABASE = "jeeproject";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Mahdiadmin20_";
    private static final String TIMEZONE = "Africa/Tunis"; // MySQL timezone format

    // Establishes a database connection
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Set the default timezone
            TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE);
            TimeZone.setDefault(timeZone);

            // Construct JDBC URL for MySQL
            String jdbcUrl = "jdbc:" + DB_CONNECTION + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_DATABASE;

            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            // Log or handle the exception appropriately
            throw new SQLException("Error connecting to the database.", e);
        }
        return connection;
    }

    // Close the database connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Log or handle the exception appropriately
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }
}
