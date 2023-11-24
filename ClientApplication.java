/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.clientapplication;

/**
 *
 * @author Денис
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ClientApplication {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8000)) {
            Scanner scanner = new Scanner(System.in);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println("Enter shape type ('Circle', 'Rectangle', 'Triangle', 'Square', 'Ellipse', 'RegularHexagon') or 'Q' to quit:");
                String shapeType = scanner.nextLine();

                if (shapeType.equalsIgnoreCase("Q")) {
                    out.writeUTF("Q");
                    break;
                }

                out.writeUTF(shapeType);
                if (shapeType.equalsIgnoreCase("Circle")) {
                    System.out.println("Enter radius:");
                    double radius = scanner.nextDouble();
                    out.writeDouble(radius);
                } else if (shapeType.equalsIgnoreCase("Rectangle")) {
                    System.out.println("Enter length:");
                    double length = scanner.nextDouble();
                    System.out.println("Enter width:");
                    double width = scanner.nextDouble();
                    out.writeDouble(length);
                    out.writeDouble(width);
                } else if (shapeType.equalsIgnoreCase("Triangle")) {
                    System.out.println("Enter base:");
                    double base = scanner.nextDouble();
                    System.out.println("Enter height:");
                    double height = scanner.nextDouble();
                    out.writeDouble(base);
                    out.writeDouble(height);
                } else if (shapeType.equalsIgnoreCase("Square")) {
                    System.out.println("Enter side length:");
                    double side = scanner.nextDouble();
                    out.writeDouble(side);
                } else if (shapeType.equalsIgnoreCase("Ellipse")) {
                    System.out.println("Enter semi-major axis length:");
                    double semiMajorAxis = scanner.nextDouble();
                    System.out.println("Enter semi-minor axis length:");
                    double semiMinorAxis = scanner.nextDouble();
                    out.writeDouble(semiMajorAxis);
                    out.writeDouble(semiMinorAxis);
                } else if (shapeType.equalsIgnoreCase("RegularHexagon")) {
                    System.out.println("Enter side length:");
                    double side = scanner.nextDouble();
                    out.writeDouble(side);
                }

                double area = in.readDouble();
                System.out.println("Received area from server: " + area);
            }

            System.out.println("Closing the connection.");
            scanner.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
