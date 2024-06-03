/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AIS_R_Initial.Controller;

/**
 *
 * @author 12217608
 */
import ComputeServer.DatabaseUtil;
import com.AIS_R_Initial.Model.AdministrationStaff;
import com.AIS_R_Initial.Model.ManagementStaff;
import com.AIS_R_Initial.Model.RecruitDetails;
import java.sql.SQLIntegrityConstraintViolationException;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;


public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String requestType = (String) ois.readObject();

             if ("RegisterAdminStaff".equals(requestType)) {
                handleAdminStaffRegistration(ois, oos);
            } else if ("RegisterManagementStaff".equals(requestType)) {
                handleManagementStaffRegistration(ois, oos);
            } else if ("RegisterRecruitDetails".equals(requestType)) {
                handleRecruitDetailsRegistration(ois, oos);
            }
            // Handle other request types here

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Exception in ClientHandler: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }
    
        private void handleRecruitDetailsRegistration(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
        System.out.println("Receiving recruit details...");
        RecruitDetails recruit = (RecruitDetails) ois.readObject();
        System.out.println("Received recruit details: " + recruit);
        saveRecruitDetailsToDatabase(recruit);
        oos.writeObject("Registration successful");
    } catch (IOException | ClassNotFoundException | SQLException e) {
        System.err.println("Exception in handleRecruitDetailsRegistration: " + e.getMessage());
        e.printStackTrace();
    }
    }
    
    

    private void handleAdminStaffRegistration(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            AdministrationStaff staff = (AdministrationStaff) ois.readObject();
            saveAdminStaffToDatabase(staff);
            oos.writeObject("Registration successful");
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println("Exception in handleAdminStaffRegistration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleManagementStaffRegistration(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            ManagementStaff staff = (ManagementStaff) ois.readObject();
            saveManagementStaffToDatabase(staff);
            oos.writeObject("Registration successful");
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println("Exception in handleManagementStaffRegistration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveAdminStaffToDatabase(AdministrationStaff staff) throws SQLException {
        String sql = "INSERT INTO AdministrationStaff (fullName, address, phoneNumber, email, username, password, positionType, staffId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, staff.getFullName());
            pstmt.setString(2, staff.getAddress());
            pstmt.setString(3, staff.getPhoneNumber());
            pstmt.setString(4, staff.getEmail());
            pstmt.setString(5, staff.getUsername());
            pstmt.setString(6, staff.getPassword());
            pstmt.setString(7, staff.getPositionType().name());
            pstmt.setString(8, staff.getStaffId());
            pstmt.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e) {
        throw new SQLException("Staff ID already exists in the database.", e);
    }
    }

    private void saveManagementStaffToDatabase(ManagementStaff staff) throws SQLException {
        String sql = "INSERT INTO ManagementStaff (fullName, address, phoneNumber, email, username, password, managementLevel, branch, staffId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, staff.getFullName());
            pstmt.setString(2, staff.getAddress());
            pstmt.setString(3, staff.getPhoneNumber());
            pstmt.setString(4, staff.getEmail());
            pstmt.setString(5, staff.getUsername());
            pstmt.setString(6, staff.getPassword());
            pstmt.setString(7, staff.getManagementLevel().name());
            pstmt.setString(8, staff.getBranch().name());
            pstmt.setString(9, staff.getStaffId());
            pstmt.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException e) {
        throw new SQLException("Staff ID already exists in the database.", e);
    }
    }
    
    private void saveRecruitDetailsToDatabase(RecruitDetails recruit) throws SQLException {
    String sql = "INSERT INTO RecruitDetails (fullName, address, phoneNumber, email, username, password, interviewDate, qualificationLevel) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, recruit.getFullName());
        pstmt.setString(2, recruit.getAddress());
        pstmt.setString(3, recruit.getPhoneNumber());
        pstmt.setString(4, recruit.getEmail());
        pstmt.setString(5, recruit.getUsername());
        pstmt.setString(6, recruit.getPassword());
        pstmt.setDate(7, new java.sql.Date(recruit.getInterviewDate().getTime()));
        pstmt.setString(8, recruit.getQualificationLevel().name());
        pstmt.executeUpdate();
        System.out.println("Recruit details saved to database successfully");
    } catch (SQLIntegrityConstraintViolationException e) {
        throw new SQLException("Username already exists in the database.", e);
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Database error occurred while saving recruit details.", e);
    }
    }
}
