package com.ais_r_initial.ais.r.initial.Service;

import com.ais_r_initial.ais.r.initial.Model.AdministrationStaff;
import com.ais_r_initial.ais.r.initial.Utility.Encryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ais_r_initial.ais.r.initial.Model.DatabaseConnector.getConnection;

public class AdministrationStaffService {
    public void insertAdminitrationStaff(AdministrationStaff staff) {
        String sql = "INSERT INTO administration_staff (fullName, address, phoneNumber, email, username, password, staffId, positionType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getAddress());
            statement.setString(3, staff.getPhoneNumber());
            statement.setString(4, staff.getEmail());
            statement.setString(5, staff.getUsername());
            statement.setString(6, staff.getPassword());
            statement.setString(7, staff.getStaffId());
            statement.setString(8, staff.getPositionType().name());

            statement.executeUpdate();
            System.out.println("AdministrationStaff record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AdministrationStaff findAdministrationStaffByUsername(String username) {
        String sql = "SELECT * FROM administration_staff WHERE username = ?";
        AdministrationStaff staff = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                staff = new AdministrationStaff(
                        resultSet.getString("fullName"),
                        resultSet.getString("address"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("staffId"),
                        AdministrationStaff.PositionType.valueOf(resultSet.getString("positionType"))
                );
                staff.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public List<AdministrationStaff> getAllAdministrationStaff() {
        List<AdministrationStaff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM administration_staff";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                AdministrationStaff staff = new AdministrationStaff(
                        resultSet.getString("fullName"),
                        resultSet.getString("address"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("staffId"),
                        AdministrationStaff.PositionType.valueOf(resultSet.getString("positionType"))
                );
                staff.setId(resultSet.getInt("id"));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public void updateAdministrationStaff(AdministrationStaff staff) {
        String sql = "UPDATE administration_staff SET fullName = ?, address = ?, phoneNumber = ?, email = ?, username = ?, password = ?, staffId = ?, positionType = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getAddress());
            statement.setString(3, staff.getPhoneNumber());
            statement.setString(4, staff.getEmail());
            statement.setString(5, staff.getUsername());
            statement.setString(6, staff.getPassword());
            statement.setString(7, staff.getStaffId());
            statement.setString(8, staff.getPositionType().name());
            statement.setInt(9, staff.getId());

            statement.executeUpdate();
            System.out.println("AdministrationStaff record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AdministrationStaff login(String username, String password) throws Exception {
        Encryptor encryptor = new Encryptor();
        AdministrationStaff administrationStaff = findAdministrationStaffByUsername(username);
        if (administrationStaff != null) {
            if (encryptor.decryptText(administrationStaff.getPassword()).equals(password)) {
                return administrationStaff;
            }
        }
        return null;
    }
}
