package carsharing;

import java.sql.*;

public class DBconnect {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:C:\\Users\\Alexander\\Car Sharing\\Car Sharing\\task\\src\\carsharing\\db\\";
    static Connection connection;
    static Statement statement;
    public static void connect() {
        try  {
            connection = DriverManager.getConnection(DB_URL + "carsharing");
            statement = connection.createStatement();
            Class.forName(JDBC_DRIVER);
            connection.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void disconnect() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addToDB(String name) {
        connect();
        try {
            statement.executeUpdate("INSERT INTO COMPANY (NAME) VALUES ('" + name + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }
}
