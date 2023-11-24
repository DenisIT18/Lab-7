/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.serverapplication;

/**
 *
 * @author Денис
 */
import java.io.DataInputStream; // Этот импорт используется для считывания примитивных типов данных из потока ввода как последовательность байтов.
import java.io.DataOutputStream; //Этот импорт испольуется для записи примитивных типов данных в поток вывода как последовательность байтов.
import java.io.IOException; //Этот импорт испольуется для включения в ваш код класса IOException, который является частью пакета java.io.
import java.net.ServerSocket; //Этот импорт используется для создания сервера, который прослушивает определенный порт (в данном случае порт 8000) и принимает входящие запросы на подключение от клиентов.
import java.net.Socket; //Этот импорт используется для соединения между клиентом и сервером.

class ServerApplication {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8000)) {
            while (true) {
                Socket client = server.accept();
                System.out.println("Connection accepted.");

                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                while (!client.isClosed()) {
                    String shapeType = in.readUTF();

                    if (shapeType.equalsIgnoreCase("Q")) {
                        System.out.println("Client requested to close the connection.");
                        break;
                    }

                    double area = 0.0;

                    if (shapeType.equalsIgnoreCase("Circle")) {
                        double radius = in.readDouble();
                        area = calculateCircleArea(radius);
                    } else if (shapeType.equalsIgnoreCase("Rectangle")) {
                        double length = in.readDouble();
                        double width = in.readDouble();
                        area = calculateRectangleArea(length, width);
                    } else if (shapeType.equalsIgnoreCase("Triangle")) {
                        double base = in.readDouble();
                        double height = in.readDouble();
                        area = calculateTriangleArea(base, height);
                    } else if (shapeType.equalsIgnoreCase("Square")) {
                        double side = in.readDouble();
                        area = calculateSquareArea(side);
                    } else if (shapeType.equalsIgnoreCase("Ellipse")) {
                        double semiMajorAxis = in.readDouble();
                        double semiMinorAxis = in.readDouble();
                        area = calculateEllipseArea(semiMajorAxis, semiMinorAxis);
                    } else if (shapeType.equalsIgnoreCase("RegularHexagon")) {
                        double side = in.readDouble();
                        area = calculateRegularHexagonArea(side);
                    }

                    out.writeDouble(area);
                    out.flush();
                }

                System.out.println("Client disconnected.");
                in.close();
                out.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculateCircleArea(double radius) {
        return Math.PI * radius * radius;
    }

    private static double calculateRectangleArea(double length, double width) {
        return length * width;
    }

    private static double calculateTriangleArea(double base, double height) {
        return 0.5 * base * height;
    }

    private static double calculateSquareArea(double side) {
        return side * side;
    }

    private static double calculateEllipseArea(double semiMajorAxis, double semiMinorAxis) {
        return Math.PI * semiMajorAxis * semiMinorAxis;
    }

    private static double calculateRegularHexagonArea(double side) {
        return 3 * Math.sqrt(3) * side * side / 2;
    }
}

