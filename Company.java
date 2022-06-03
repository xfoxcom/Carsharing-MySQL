package carsharing;

import java.util.Scanner;

public class Company {
    public static void inCompany(String name, int id) {
        Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n" + name + " company\n1. Car list\n2. Create a car\n0. Back");
                int enter = scanner.nextInt();
                if (enter == 0) {
                    break;
                }
                if (enter == 1) {
                    Car.listCar(id);
                }
                if (enter == 2) {
                    Car.addCar(id, name);
                }
            }
    }
}
