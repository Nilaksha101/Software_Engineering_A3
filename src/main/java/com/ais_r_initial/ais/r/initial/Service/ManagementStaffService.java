package com.ais_r_initial.ais.r.initial.Service;

import com.ais_r_initial.ais.r.initial.Model.ManagementStaff;
import com.ais_r_initial.ais.r.initial.Utility.Encryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ais_r_initial.ais.r.initial.Model.DatabaseConnector.getConnection;

public class ManagementStaffService {
    public void insertManagementStaff(ManagementStaff staff) {
        String sql = "INSERT INTO management_staff (fullName, address, phoneNumber, email, username, password, staffId, managementLevel, branch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getAddress());
            statement.setString(3, staff.getPhoneNumber());
            statement.setString(4, staff.getEmail());
            statement.setString(5, staff.getUsername());
            statement.setString(6, staff.getPassword());
            statement.setString(7, staff.getStaffId());
            statement.setString(8, staff.getManagementLevel().name());
            statement.setString(9, staff.getBranch().name());

            statement.executeUpdate();
            System.out.println("ManagementStaff record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public ManagementStaff findManagementStaffByUsername(String username) {
        String sql = "SELECT * FROM management_staff WHERE username = ?";
        ManagementStaff staff = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                staff = new ManagementStaff(
                        resultSet.getString("fullName"),
                        resultSet.getString("address"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("staffId"),
                        ManagementStaff.ManagementLevel.valueOf(resultSet.getString("managementLevel")),
                        ManagementStaff.Branch.valueOf(resultSet.getString("branch"))
                );
                staff.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }


    public List<ManagementStaff> getAllManagementStaff() {
        List<ManagementStaff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM management_staff";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ManagementStaff staff = new ManagementStaff(
                        resultSet.getString("fullName"),
                        resultSet.getString("address"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("staffId"),
                        ManagementStaff.ManagementLevel.valueOf(resultSet.getString("managementLevel")),
                        ManagementStaff.Branch.valueOf(resultSet.getString("branch"))
                );
                staff.setId(resultSet.getInt("id"));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public void updateManagementStaff(ManagementStaff staff) {
        String sql = "UPDATE management_staff SET fullName = ?, address = ?, phoneNumber = ?, email = ?, username = ?, password = ?, staffId = ?, managementLevel = ?, branch = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getAddress());
            statement.setString(3, staff.getPhoneNumber());
            statement.setString(4, staff.getEmail());
            statement.setString(5, staff.getUsername());
            statement.setString(6, staff.getPassword());
            statement.setString(7, staff.getStaffId());
            statement.setString(8, staff.getManagementLevel().name());
            statement.setString(9, staff.getBranch().name());
            statement.setInt(10, staff.getId());

            statement.executeUpdate();
            System.out.println("ManagementStaff record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ManagementStaff login(String username, String password) throws Exception {
        Encryptor encryptor = new Encryptor();
        ManagementStaff managementStaff = findManagementStaffByUsername(username);
        if (managementStaff != null) {

            if (encryptor.decryptText(managementStaff.getPassword()).equals(password)) {
                return managementStaff;
            }
        }
        return null;
    }
}
