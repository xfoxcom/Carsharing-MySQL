package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Car {
    public static void addCar(int id, String compName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter the car name: ");
        String name = scanner.nextLine();
        try {
            DBconnect.statement.executeUpdate("INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('" + name + "', " + id + ");");
            //DBconnect.statement.executeUpdate("INSERT INTO CAR_" + compName + "(NAME) VALUES ('"+ name +"')");
            System.out.println("The car was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void listCar(int id) {
        int i = 0;
        int k = 1;
        String Name = "";
        try {
         ResultSet resultSet = DBconnect.statement.executeQuery("SELECT NAME, ID FROM CAR WHERE COMPANY_ID = " + id);
while (resultSet.next()) {
    i++;
}
if (i == 0) {
    System.out.println("\nThe car list is empty!");
} else {
    ResultSet companyName = DBconnect.statement.executeQuery("SELECT NAME FROM COMPANY WHERE ID = " + id);
    if (companyName.next()) {
        Name = companyName.getString("NAME");
    }
    resultSet = DBconnect.statement.executeQuery("SELECT NAME, ID FROM CAR WHERE COMPANY_ID = " + id);

    System.out.println("\n" + Name + " cars:");
    while (resultSet.next()) {
        System.out.println(k++ + ". " + resultSet.getString("NAME"));
    }
}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
