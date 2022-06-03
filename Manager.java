package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Manager {
    public static void loginManager() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Company list\n2. Create a company\n0. Back");
            int enter = scanner.nextInt();
            if(enter == 0) {
                break;
            }
            if (enter == 1) {
                listCompanies();
            }
            if (enter == 2) {
                createComp();
            }
        }
        System.out.println();
    }
    public static void listCompanies() {
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        DBconnect.connect();
        try {
            ResultSet resultSet = DBconnect.statement.executeQuery("SELECT * from COMPANY");
            while (resultSet.next()) {
                i++;
            }
            if (i == 0) {
                System.out.println("\nThe company list is empty!");
            } else {
                System.out.println("\nChoose the company: ");
                resultSet = DBconnect.statement.executeQuery("SELECT * from COMPANY");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("ID") + ". " + resultSet.getString("NAME"));
                }
                System.out.println("0. Back");
                int id = scanner.nextInt();
                if (id == 0) {
                    System.out.print("");
                } else {
                    resultSet = DBconnect.statement.executeQuery("SELECT NAME from COMPANY where ID = " + id);
                    if (resultSet.next()) {
                        Company.inCompany(resultSet.getString("NAME"), id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBconnect.disconnect();
    }
    public static void createComp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter the company name: ");
        String name = scanner.nextLine();
        DBconnect.connect();
        try {
            DBconnect.statement.executeUpdate("CREATE TABLE IF NOT EXISTS CAR_" + name + " (ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR NOT NULL UNIQUE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBconnect.addToDB(name);
        System.out.println("The company was created!");
    }
}
