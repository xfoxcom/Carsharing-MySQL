package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer {
    public static void createCustomer() {
        Scanner scanner = new Scanner(System.in);
        DBconnect.connect();
        System.out.println("\nEnter customer name: ");
        String name = scanner.nextLine();
        try {
            DBconnect.statement.executeUpdate("INSERT INTO CUSTOMER (NAME) VALUES ('" + name + "');");
            System.out.println("The customer was added!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
DBconnect.disconnect();
    }
    public static void logInCustomer() {
        Scanner scanner = new Scanner(System.in);
        DBconnect.connect();
        int i = 0;
        try {
            ResultSet resultSet = DBconnect.statement.executeQuery("SELECT * FROM CUSTOMER");
            while (resultSet.next()) {
                i++;
            }
            if (i == 0) {
                System.out.println("The customer list is empty!");
            } else {

                System.out.println("\nCustomer list: ");
                ResultSet customer = DBconnect.statement.executeQuery("SELECT ID, NAME FROM CUSTOMER");
                while (customer.next()) {
                    System.out.println(customer.getInt("ID") + ". " + customer.getString("NAME"));
                }
                System.out.println("0. Back");
                int k = scanner.nextInt();
                if (k == 0) {
                    System.out.print("\n");
                } else {
                   customerActivity(k);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBconnect.disconnect();

    }
    public static void customerActivity(int id) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");
            int k = scanner.nextInt();
            if (k == 0) {
                System.out.println();
                break;
            }
            if (k == 1) {
                try {
                    int temp = 0;
                    ResultSet isRent = DBconnect.statement.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + id);
                    if (isRent.next()) {
                        temp = isRent.getInt("RENTED_CAR_ID");
                    }
                    if (temp > 0) {
                        System.out.println("\nYou've already rented a car!");
                    } else {
                        System.out.println("\nChoose a company: ");
                        ResultSet choose = DBconnect.statement.executeQuery("SELECT ID, NAME FROM COMPANY");
                        while (choose.next()) {
                            System.out.println(choose.getInt("ID") + ". " + choose.getString("NAME"));
                        }
                        System.out.println("0. Back");
                        int ch = scanner.nextInt();
                        if (ch == 0) {
                            System.out.println();
                        } else {
                            System.out.println("\nChoose a car: ");
                            ResultSet name = DBconnect.statement.executeQuery("SELECT NAME FROM COMPANY WHERE ID = " + ch);
                            String compN = "";
                            if (name.next()) {
                                compN = name.getString("NAME");
                            }
                            ResultSet car = DBconnect.statement.executeQuery("SELECT ID, NAME FROM CAR_" + compN);
                            while (car.next()) {
                                System.out.println(car.getInt("ID") + ". " + car.getString("NAME"));
                            }
                            System.out.println("0. Back");
                            int enter = scanner.nextInt();
                            if (enter == 0) { // car's id
                                System.out.println();
                            } else {
                                ResultSet resultSet = DBconnect.statement.executeQuery("SELECT NAME FROM CAR_" + compN + " WHERE ID = " + enter);
                                if (resultSet.next()) {
                                    String a = resultSet.getString("NAME");
                                    System.out.println("\nYou rented '" + a + "'");
                                    ResultSet resultSet1 = DBconnect.statement.executeQuery("SELECT ID FROM CAR WHERE NAME = '" + a + "'");
                                    if (resultSet1.next()) {
                                        DBconnect.statement.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = " + resultSet1.getInt("ID"));
                                    }
                                }
                            }
                        }
                        }

                    } catch(SQLException e){
                        e.printStackTrace();
                    }

            }
            if (k == 2) {
                try {
                    ResultSet isRent = DBconnect.statement.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + id);
                    if (isRent.next()) {
                        if (isRent.getInt("RENTED_CAR_ID") == 0) {
                            System.out.println("\nYou didn't rent a car!");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (k == 3) {
                try {
                    ResultSet isRent = DBconnect.statement.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + id);
                    if (isRent.next()) {
                        if (isRent.getInt("RENTED_CAR_ID") == 0) {
                            System.out.println("\nYou didn't rent a car!");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
