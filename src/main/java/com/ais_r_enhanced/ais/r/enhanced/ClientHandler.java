package com.ais_r_enhanced.ais.r.enhanced;



import com.ais_r_enhanced.ais.r.enhanced.Model.AdministrationStaff;
import com.ais_r_enhanced.ais.r.enhanced.Model.ManagementStaff;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDetails;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDto;
import com.ais_r_enhanced.ais.r.enhanced.Service.AdministrationStaffService;
import com.ais_r_enhanced.ais.r.enhanced.Service.ManagementStaffService;
import com.ais_r_enhanced.ais.r.enhanced.Service.RecruitService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.List;



/**
 *
 * @author 12217608
 */

public class ClientHandler implements Runnable {

    private final AdministrationStaffService administrationStaffService = new AdministrationStaffService();
    private final ManagementStaffService managementStaffService = new ManagementStaffService();
    private final RecruitService recruitService = new RecruitService();
    private final Socket socket;
    private static Connection connection;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

            String action = input.readUTF();

            if ("AdminLogin".equals(action)) {
                String username = (String) input.readObject();
                String password = (String) input.readObject();
                AdministrationStaff administrationStaff = administrationStaffService.login(username,password);
                output.writeObject(administrationStaff);
            }
            if ("ManagementLogin".equals(action)) {
                String username = (String) input.readObject();
                String password = (String) input.readObject();
                ManagementStaff managementStaff = managementStaffService.login(username,password);
                output.writeObject(managementStaff);
            }
            if ("RecruitLogin".equals(action)) {
                String username = (String) input.readObject();
                String password = (String) input.readObject();
                RecruitDetails recruitDetails = recruitService.login(username,password);
                output.writeObject(recruitDetails);
            }
            if ("AdminRegistration".equals(action)) {
                AdministrationStaff administrationStaff = (AdministrationStaff) input.readObject();
                administrationStaffService.insertAdminitrationStaff(administrationStaff);
                output.writeObject(true);
            }
            if ("ManagementRegistration".equals(action)) {
                ManagementStaff managementStaff = (ManagementStaff) input.readObject();
                managementStaffService.insertManagementStaff(managementStaff);
                output.writeObject(true);
            }
            if ("RecruitRegistration".equals(action)) {
                RecruitDetails recruitDetails = (RecruitDetails) input.readObject();
                recruitService.insertRecruitDetails(recruitDetails);
                output.writeObject(true);
            }
            if ("findRecruitDetailsByUsername".equals(action)) {
                String username = (String) input.readObject();
                RecruitDetails recruitDetails = recruitService.findRecruitDetailsByUsername(username);
                output.writeObject(recruitDetails);
            }
            if ("setRequestForOTPInDB".equals(action)) {
                RecruitDetails recruitDetails = (RecruitDetails) input.readObject();
                recruitService.setRequestForOTPInDB(recruitDetails);
                output.writeObject(true);
            }
            if ("getAllOTPRequestRecruit".equals(action)) {
                List<RecruitDetails> recruitDetails = recruitService.getAllOTPRequestRecruit();
                output.writeObject(recruitDetails);
            }
            if ("setOTPInDB".equals(action)) {
                List<RecruitDetails> recruitDetails = (List<RecruitDetails>) input.readObject();
                recruitService.setOTPInDB(recruitDetails);
                output.writeObject(recruitDetails);
            }
            if ("updateRecruitDetails".equals(action)) {
                RecruitDetails recruitDetails = (RecruitDetails) input.readObject();
                recruitService.updateRecruitDetails(recruitDetails);
                output.writeObject(true);
            }
            if ("findRecruitDetailsByFullName".equals(action)) {
                String fullName = (String) input.readObject();
                RecruitDetails recruitDetails = recruitService.findRecruitDetailsByFullName(fullName);
                output.writeObject(recruitDetails);
            }
            if("getAllRecruitSortByQualificationLevel".equals(action)){
                List<RecruitDetails> recruitDetails = recruitService.getAllRecruitSortByQualificationLevel();
                output.writeObject(recruitDetails);
            }
            if("getAllRecruitSortByLastNameDescAndGroupByLocation".equals(action)){
                List<RecruitDto> recruitDetails = recruitService.getAllRecruitSortByLastNameDescAndGroupByLocation();
                output.writeObject(recruitDetails);
            }
            if("DeleteRecruitDetail".equals(action)){
                int id = (int) input.readObject();
                recruitService.deleteRecruitById(id);
                output.writeObject(true);
            }
            if ("getAllManagementStaff".equals(action)) {
                List<ManagementStaff> managementStaffs = managementStaffService.getAllManagementStaff();
                output.writeObject(managementStaffs);
            }if("getAllRecruitDetails".equals(action)){
                List<RecruitDetails> recruitDetails = recruitService.getAllRecruitDetails();
                output.writeObject(recruitDetails);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean authenticateUser(String username, String password) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ais_r_db", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeDatabaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
