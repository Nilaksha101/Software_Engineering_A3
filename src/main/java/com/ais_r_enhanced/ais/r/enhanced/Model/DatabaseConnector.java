package com.ais_r_enhanced.ais.r.enhanced.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author 12217608
 */
public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "ais_r_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void initializeDatabase() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement statement = connection.createStatement()) {

            // Check if the database exists
            String checkDatabaseQuery = "SHOW DATABASES LIKE '" + DB_NAME + "'";
            var resultSet = statement.executeQuery(checkDatabaseQuery);
            if (!resultSet.next()) {
                // Database does not exist, create it
                String createDatabaseQuery = "CREATE DATABASE " + DB_NAME;
                statement.executeUpdate(createDatabaseQuery);
                System.out.println("Database " + DB_NAME + " created successfully.");


            } else {
                System.out.println("Database " + DB_NAME + " already exists.");
            }
            try (Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD); Statement stmt = conn.createStatement()) {
                // Create tables if not exists
                stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS administration_staff (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            fullName VARCHAR(255) NOT NULL,
                            address VARCHAR(255) NOT NULL,
                            phoneNumber VARCHAR(15) NOT NULL,
                            email VARCHAR(255) NOT NULL UNIQUE,
                            username VARCHAR(50) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL,
                            staffId VARCHAR(50) NOT NULL UNIQUE,
                            positionType ENUM('FULL_TIME', 'PART_TIME', 'VOLUNTEER') NOT NULL
                        );""");
                stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS management_staff (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            fullName VARCHAR(255) NOT NULL,
                            address VARCHAR(255) NOT NULL,
                            phoneNumber VARCHAR(15) NOT NULL,
                            email VARCHAR(255) NOT NULL UNIQUE,
                            username VARCHAR(50) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL,
                            staffId VARCHAR(50) NOT NULL UNIQUE,
                            managementLevel ENUM('SENIOR_MANAGER', 'MID_LEVEL_MANAGER', 'SUPERVISOR') NOT NULL,
                            branch ENUM('MELBOURNE', 'SYDNEY', 'BRISBANE', 'ADELAIDE') NOT NULL
                        );""");
                stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS recruits (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            fullName VARCHAR(255) NOT NULL,
                            address VARCHAR(255) NOT NULL,
                            phoneNumber VARCHAR(20) NOT NULL,
                            email VARCHAR(255) NOT NULL,
                            username VARCHAR(50) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL,
                            interviewDate DATE NOT NULL,
                            qualificationLevel ENUM('BACHELORS', 'MASTERS', 'PHD') NOT NULL,
                            department ENUM('SoftwareEngineer', 'AerospaceEngineer', 'MechanicalEngineer', 'ElectronicsEngineer'),
                            experience VARCHAR(255) NOT NULL,
                            timeToLeadAndCompleteProject VARCHAR(255) NOT NULL,
                            requestForOTP TINYINT(1),
                            OTPCode VARCHAR(10),
                            location ENUM('MELBOURNE', 'SYDNEY', 'BRISBANE', 'ADELAIDE') NOT NULL
                        );""");


                // Other table creations
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD);
    }


}
