package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:C:\\Users\\Alexander\\Car Sharing\\Car Sharing\\task\\src\\carsharing\\db\\";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String compName = "COMPANY";
        if (args[1]!=null) {
             compName = args[1];
        }

        try (Connection connection = DriverManager.getConnection(DB_URL + compName)) {

            try (Statement statement = connection.createStatement()) {
                Class.forName(JDBC_DRIVER);
                connection.setAutoCommit(true);
               /* statement.executeUpdate("DROP TABLE IF EXISTS CUSTOMER");
                statement.executeUpdate("DROP TABLE IF EXISTS CAR");
                statement.executeUpdate("DROP TABLE IF EXISTS COMPANY");*/

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY (ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR NOT NULL UNIQUE)");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS CAR (ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR NOT NULL UNIQUE, COMPANY_ID INTEGER NOT NULL," +
                        " CONSTRAINT fk_id FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS CUSTOMER (ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR NOT NULL UNIQUE, RENTED_CAR_ID INTEGER DEFAULT NULL," +
                        " CONSTRAINT FK_RENT FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.println("1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit");
            int enter = scanner.nextInt();
            if (enter == 0) {
                break;
            }
            if (enter == 1) {
                Manager.loginManager();
            }
            if (enter == 2) {
                Customer.logInCustomer();
            }
            if (enter == 3) {
                Customer.createCustomer();
            }
        }
    }
}