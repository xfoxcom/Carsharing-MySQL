package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Customer {
    public static void createCustomer() {
        Scanner scanner = new Scanner(System.in);
        DBconnect.connect();
        System.out.println("\nEnter the customer name: ");
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
                System.out.println("\nThe customer list is empty!\n");
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
                        int ch = scanner.nextInt(); // id of company
                        if (ch == 0) {
                            System.out.println();
                        } else {
                            System.out.println("\nChoose a car: ");
                            String customersChoose = "";
                            int f = 1;
                            Map<Integer, String> cars = new LinkedHashMap<>();
                                ResultSet car1 = DBconnect.statement.executeQuery("SELECT NAME FROM CAR WHERE COMPANY_ID = " + ch + " AND RENTED IS NOT TRUE");
                                while (car1.next()) {
                                    cars.put(f++, car1.getString("NAME"));
                                }
                                for (Map.Entry<Integer, String> entry : cars.entrySet()) {
                                    System.out.println(entry.getKey() + ". " + entry.getValue());
                                }
                          //  }
                            System.out.println("0. Back");
                            int enter = scanner.nextInt();
                            if (enter == 0) { // car's key
                                System.out.println();
                            } else {
                                for (Map.Entry<Integer, String> entry : cars.entrySet()) {
                                    if (entry.getKey() == enter) {
                                        customersChoose = entry.getValue();
                                    }
                                }
                                    System.out.println("\nYou rented '" + customersChoose + "'");
                                    ResultSet resultSet1 = DBconnect.statement.executeQuery("SELECT ID FROM CAR WHERE NAME = '" + customersChoose + "'");
                                    if (resultSet1.next()) {
                                        DBconnect.statement.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = " + resultSet1.getInt("ID") + " WHERE ID = " + id);
                                        DBconnect.statement.executeUpdate("UPDATE CAR SET RENTED = TRUE WHERE NAME = '" + customersChoose + "'");
                                    }
                            }
                        }
                        }

                    } catch(SQLException e){
                        e.printStackTrace();
                    }
            }
            if (k == 2) {
                boolean OK = true;
                try {
                    ResultSet isRent = DBconnect.statement.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + id);
                    if (isRent.next()) {
                        if (isRent.getInt("RENTED_CAR_ID") == 0) {
                            OK = false;
                            System.out.println("\nYou didn't rent a car!");
                        }
                    }
                    if (OK){
                        DBconnect.statement.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL");
                        System.out.println("\nYou've returned a rented car!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (k == 3) {
                boolean OK = true;
                int RCI = 0;
                try {
                    ResultSet isRent = DBconnect.statement.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + id);
                    if (isRent.next()) {
                        if (isRent.getInt("RENTED_CAR_ID") == 0) {
                            OK = false;
                            System.out.println("\nYou didn't rent a car!");
                        }
                    }
                    if (OK) {
                        ResultSet rented = DBconnect.statement.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + id);
                        if (rented.next()) {
                            RCI = rented.getInt("RENTED_CAR_ID");
                        }
                            rented = DBconnect.statement.executeQuery("SELECT NAME FROM CAR WHERE ID = " + RCI);
                            if (rented.next()) {
                                System.out.println("\nYour rented car: \n" + rented.getString("NAME"));
                            }
                            rented = DBconnect.statement.executeQuery("SELECT COMPANY_ID FROM CAR WHERE ID = " + RCI);
                        if (rented.next()) {
                           ResultSet rented1 = DBconnect.statement.executeQuery("SELECT NAME FROM COMPANY WHERE ID = " + rented.getInt("COMPANY_ID"));
                           if (rented1.next()) {
                               System.out.println("Company: \n" + rented1.getString("NAME"));
                           }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
