/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ComputeServer;

/**
 *
 * @author 12217608
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void setupDatabase() {
        try (Connection connection = DatabaseUtil.getConnection(); Statement statement = connection.createStatement()) {
            // Create database if it doesn't exist
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS ais_r_db");

            // Use the database
            statement.executeUpdate("USE ais_r_db");

            // Create tables if they don't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS AdministrationStaff ("
                + "staffId INT AUTO_INCREMENT PRIMARY KEY, "
                + "fullName VARCHAR(100), "
                + "address VARCHAR(200), "
                + "phoneNumber VARCHAR(15), "
                + "email VARCHAR(100), "
                + "username VARCHAR(50), "
                + "password VARCHAR(50), "
                + "positionType ENUM('FULL_TIME', 'PART_TIME', 'VOLUNTEER'))");
            
                // Create ManagementStaff table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ManagementStaff ("
                + "staffId INT AUTO_INCREMENT PRIMARY KEY, "
                + "fullName VARCHAR(100), "
                + "address VARCHAR(200), "
                + "phoneNumber VARCHAR(15), "
                + "email VARCHAR(100), "
                + "username VARCHAR(50), "
                + "password VARCHAR(50), "
                + "managementLevel ENUM('SENIOR_MANAGER', 'MID_LEVEL_MANAGER', 'SUPERVISOR'), "
                + "branch ENUM('MELBOURNE', 'SYDNEY', 'BRISBANE', 'ADELAIDE'))");
            
      
           // Create RecruitDetails table if it doesn't exist
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS RecruitDetails (" 
                + "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "fullName VARCHAR(100), " +
                "address VARCHAR(200), " +
                "phoneNumber VARCHAR(15), " +
                "email VARCHAR(100), " +
                "username VARCHAR(50), " +
                "password VARCHAR(50), " +
                "interviewDate DATE, " +
                "qualificationLevel ENUM('BACHELORS', 'MASTERS', 'PHD'), " +
                "department VARCHAR(100))"); 
                
                 // Create UserCredentials table if it doesn't exist
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS UserCredentials ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) UNIQUE NOT NULL, "
                    + "password VARCHAR(100) NOT NULL, "
                    + "authenticationCode VARCHAR(10) NOT NULL)");
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}