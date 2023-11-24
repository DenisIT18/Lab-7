/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.databases;

/**
 *
 * @author Денис
 */
import java.sql.*;
class BankSystem {
    private static final String JDBC_URL = "D:\\Presentations for University\\2 course\\Java Programming language\\practice\\Project";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);


            System.out.println("Creating statement...");
            statement = connection.createStatement();

            String createCustomersTableSQL = "CREATE TABLE IF NOT EXISTS Customers (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100)," +
                    "email VARCHAR(100)" +
                    ")";
            statement.executeUpdate(createCustomersTableSQL);
            System.out.println("Table 'Customers' created (if not exists)");

            String createAccountsTableSQL = "CREATE TABLE IF NOT EXISTS Accounts (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "customer_id INT," +
                    "balance DECIMAL(10,2)," +
                    "FOREIGN KEY (customer_id) REFERENCES Customers(id)" +
                    ")";
            statement.executeUpdate(createAccountsTableSQL);
            System.out.println("Table 'Accounts' created (if not exists)");


            String insertCustomersSQL = "INSERT INTO Customers (name, email) VALUES " +
                    "('Alice', 'alice@example.com')," +
                    "('Bob', 'bob@example.com')";
            statement.executeUpdate(insertCustomersSQL);
            System.out.println("Data inserted into 'Customers' table");
            
            String insertAccountsSQL = "INSERT INTO Accounts (customer_id, balance) VALUES " +
                    "(1, 5000.00)," +
                    "(2, 8000.00)";
            statement.executeUpdate(insertAccountsSQL);
            System.out.println("Data inserted into 'Accounts' table");
            
            String selectSQL = "SELECT c.id AS customer_id, c.name AS customer_name, c.email, " +
                    "a.id AS account_id, a.balance " +
                    "FROM Customers c " +
                    "LEFT JOIN Accounts a ON c.id = a.customer_id";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String customerName = resultSet.getString("customer_name");
                String email = resultSet.getString("email");
                int accountId = resultSet.getInt("account_id");
                double balance = resultSet.getDouble("balance");

                System.out.println("Customer ID: " + customerId + ", Name: " + customerName +
                        ", Email: " + email + ", Account ID: " + accountId + ", Balance: " + balance);
            }


            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
