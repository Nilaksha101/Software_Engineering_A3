package com.ais_r_initial.ais.r.initial.Service;

import com.ais_r_initial.ais.r.initial.Model.RecruitDetails;
import com.ais_r_initial.ais.r.initial.Utility.Encryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ais_r_initial.ais.r.initial.Model.DatabaseConnector.getConnection;

public class RecruitService {
    public void insertRecruitDetails(RecruitDetails recruit) {
        String sql = "INSERT INTO recruits (fullName, address, phoneNumber, email, username, password, interviewDate, qualificationLevel, experience, timeToLeadAndCompleteProject) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, recruit.getFullName());
            statement.setString(2, recruit.getAddress());
            statement.setString(3, recruit.getPhoneNumber());
            statement.setString(4, recruit.getEmail());
            statement.setString(5, recruit.getUsername());
            statement.setString(6, recruit.getPassword());
            statement.setDate(7, new java.sql.Date(recruit.getInterviewDate().getTime()));
            statement.setString(8, recruit.getQualificationLevel().name());
            statement.setString(9, recruit.getExperience());
            statement.setString(10, recruit.getTimeToLeadAndCompleteProject());

            statement.executeUpdate();
            System.out.println("RecruitDetails record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RecruitDetails findRecruitDetailsByFullName(String fullName) {
        String sql = "SELECT * FROM recruits WHERE UPPER(fullName) like UPPER(?)";
        RecruitDetails recruit = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fullName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("department") != null) {
                    recruit = extractRecruitDetail(resultSet);
                } else {
                    recruit = extractRecruitDetailWithoutDepartment(resultSet);
                }
                recruit.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recruit;
    }


    public RecruitDetails findRecruitDetailsByUsername(String username) {
        String sql = "SELECT * FROM recruits WHERE username = ?";
        RecruitDetails recruit = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("department") != null) {
                    recruit = extractRecruitDetail(resultSet);
                } else {
                    recruit = extractRecruitDetailWithoutDepartment(resultSet);
                }
                recruit.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recruit;
    }


    public List<RecruitDetails> getAllRecruitDetails() {
        List<RecruitDetails> recruitList = new ArrayList<>();
        String sql = "SELECT * FROM recruits";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RecruitDetails recruit;
                if (resultSet.getString("department") != null) {
                    recruit = extractRecruitDetail(resultSet);
                } else {
                    recruit = extractRecruitDetailWithoutDepartment(resultSet);
                }
                recruit.setId(resultSet.getInt("id"));
                recruitList.add(recruit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recruitList;
    }

    private RecruitDetails extractRecruitDetail(ResultSet resultSet) throws SQLException {
        RecruitDetails recruit = new RecruitDetails(
                resultSet.getString("fullName"),
                resultSet.getString("address"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("email"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getDate("interviewDate"),
                RecruitDetails.QualificationLevel.valueOf(resultSet.getString("qualificationLevel")),
                RecruitDetails.Department.valueOf(resultSet.getString("department")),
                resultSet.getString("experience"),
                resultSet.getString("timeToLeadAndCompleteProject")
        );
        recruit.setOTPCode(resultSet.getString("OTPCode"));
        return recruit;
    }

    private RecruitDetails extractRecruitDetailWithoutDepartment(ResultSet resultSet) throws SQLException {
        RecruitDetails recruit = new RecruitDetails(
                resultSet.getString("fullName"),
                resultSet.getString("address"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("email"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getDate("interviewDate"),
                RecruitDetails.QualificationLevel.valueOf(resultSet.getString("qualificationLevel")),
                null,
                resultSet.getString("experience"),
                resultSet.getString("timeToLeadAndCompleteProject")
        );
        recruit.setOTPCode(resultSet.getString("OTPCode"));
        return recruit;
    }

    public void updateRecruitDetails(RecruitDetails recruit) {
        String sql = "UPDATE recruits SET fullName = ?, address = ?, phoneNumber = ?, email = ?, username = ?, password = ?, interviewDate = ?, qualificationLevel = ?, department = ?, experience = ?, timeToLeadAndCompleteProject = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, recruit.getFullName());
            statement.setString(2, recruit.getAddress());
            statement.setString(3, recruit.getPhoneNumber());
            statement.setString(4, recruit.getEmail());
            statement.setString(5, recruit.getUsername());
            statement.setString(6, recruit.getPassword());
            statement.setDate(7, new java.sql.Date(recruit.getInterviewDate().getTime()));
            statement.setString(8, recruit.getQualificationLevel().name());
            statement.setString(9, recruit.getDepartment().name());
            statement.setString(10, recruit.getExperience());
            statement.setString(11, recruit.getTimeToLeadAndCompleteProject());
            statement.setInt(12, recruit.getId());

            statement.executeUpdate();
            System.out.println("RecruitDetails record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RecruitDetails> getAllOTPRequestRecruit() {
        List<RecruitDetails> recruitList = new ArrayList<>();
        String sql = "SELECT * FROM recruits where requestForOTP = 1";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RecruitDetails recruit;
                if (resultSet.getString("department") != null) {
                    recruit = extractRecruitDetail(resultSet);
                } else {
                    recruit = extractRecruitDetailWithoutDepartment(resultSet);
                }
                recruit.setId(resultSet.getInt("id"));
                recruitList.add(recruit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recruitList;
    }

    public List<RecruitDetails> getAllRecruitSortByLastNameDescAndGroupByLocation() {
        List<RecruitDetails> recruitList = new ArrayList<>();
        String sql = """
                SELECT * FROM recruits
                 ORDER BY SUBSTRING_INDEX(fullName, ' ', -1) DESC;
                """;
//        ORDER BY location,

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RecruitDetails recruit;
                if (resultSet.getString("department") != null) {
                    recruit = extractRecruitDetail(resultSet);
                } else {
                    recruit = extractRecruitDetailWithoutDepartment(resultSet);
                }
                recruit.setId(resultSet.getInt("id"));
                recruitList.add(recruit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recruitList;
    }

    public List<RecruitDetails> getAllRecruitSortByQualificationLevel() {
        List<RecruitDetails> recruitList = new ArrayList<>();
        String sql = """
                SELECT * FROM recruits
                ORDER BY CASE qualificationLevel
                    WHEN 'PHD' THEN 1
                    WHEN 'MASTERS' THEN 2
                    WHEN 'BACHELORS' THEN 3
                    ELSE 4
                END;
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RecruitDetails recruit;
                if (resultSet.getString("department") != null) {
                    recruit = extractRecruitDetail(resultSet);
                } else {
                    recruit = extractRecruitDetailWithoutDepartment(resultSet);
                }
                recruit.setId(resultSet.getInt("id"));
                recruitList.add(recruit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recruitList;
    }

    public void setRequestForOTPInDB(RecruitDetails recruitDetails) {
        String sql = "UPDATE recruits SET requestForOTP = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, recruitDetails.getRequestForOTP());
            statement.setInt(2, recruitDetails.getId());

            statement.executeUpdate();
            System.out.println("RecruitDetails record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOTPInDB(List<RecruitDetails> recruitDetails) {
        recruitDetails.stream().forEach(recruit -> {


            Random random = new Random();

            // Generate a random integer between 1000 and 9999
            int randomNum = random.nextInt(9000) + 1000;

            String sql = "UPDATE recruits SET OTPCode = ? WHERE id = ?";

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, String.valueOf(randomNum));
                statement.setInt(2, recruit.getId());

                statement.executeUpdate();
                System.out.println("RecruitDetails record updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public RecruitDetails login(String username, String password) throws Exception {
        Encryptor encryptor = new Encryptor();
        RecruitDetails recruitDetails = findRecruitDetailsByUsername(username);
        if (recruitDetails != null) {
            if (encryptor.decryptText(recruitDetails.getPassword()).equals(password)) {
                return recruitDetails;
            }
        }
        return null;
    }

}
