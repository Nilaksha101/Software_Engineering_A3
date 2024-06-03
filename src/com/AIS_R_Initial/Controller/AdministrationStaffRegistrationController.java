/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.AIS_R_Initial.Controller;

import ComputeServer.DatabaseUtil;
import com.AIS_R_Initial.Model.AdministrationStaff;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class AdministrationStaffRegistrationController implements Initializable {

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ComboBox<String> positionTypeComboBox;

    @FXML
    private TextField staffIdField;

    @FXML
    private TextField usernameField;

    @FXML
    private Button registerButton;

    private List<AdministrationStaff> staffList = new ArrayList<>();//list where data is saved

    @FXML
    public void Register(ActionEvent event) {

        // Check if any required fields are empty
        if (fullNameField.getText().isEmpty() || addressField.getText().isEmpty()
                || phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty()
                || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
                 || positionTypeComboBox.getValue() == null) {
            // Show an error alert 
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incomplete Information");
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        // Check if the username starts with 'A' or 'a'
        String username = usernameField.getText();
        if (!username.toLowerCase().startsWith("a")) {
            // Show an error alert if the username doesn't start with 'A' or 'a'
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Username");
            alert.setContentText("Username must start with 'A' or 'a'.");
            alert.showAndWait();
            return;
        }
        String name = fullNameField.getText();
        if (name.matches(".*\\d.*")) {
            // Show an error alert if full name contains digits
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Full Name");
            alert.setContentText("Full Name cannot contain digits.");
            alert.showAndWait();
            return;
        }
        // Check if phone number contains only digits and has 10 digits
        String phoneNumber = phoneNumberField.getText();
        if (!phoneNumber.matches("\\d{10}")) {
            // Show an error alert if phone number is invalid
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Phone Number");
            alert.setContentText("Phone Number must contain exactly 10 digits and no letters.");
            alert.showAndWait();
            return;
        }
        // Check if password has at least 3 characters
        String password = passwordField.getText();
        if (password.length() < 3) {
            // Show an error alert if password is too short
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Password");
            alert.setContentText("Password must have at least 3 characters.");
            alert.showAndWait();
            return;
        }

        //emali validation
        String email = emailField.getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!(email.matches(emailRegex))) {
            // Show an error alert if email is invalid
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email Address");
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
            return;
        }
        //AdministrationStaff administrationStaff = new AdministrationStaff(fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), staffIdField.getText(), AdministrationStaff.PositionType.valueOf(positionTypeComboBox.getValue()));
        // Add the Staff object to the list
       // staffList.add(administrationStaff);

        // Save the data to the file
       // saveDataToCSV();

//        // Show a success alert
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("Success");
//        alert.setHeaderText("Registration Successful");
//        alert.setContentText("The AdministrationStaff has been registered successfully.");
//        alert.showAndWait();
//        
       // Create AdministrationStaff object
        AdministrationStaff administrationStaff = new AdministrationStaff(
            fullNameField.getText(), 
            addressField.getText(), 
            phoneNumberField.getText(), 
            emailField.getText(), 
            usernameField.getText(), 
            passwordField.getText(), 
            staffIdField.getText(), 
            AdministrationStaff.PositionType.valueOf(positionTypeComboBox.getValue())
        );

        // Send the data to the server
        registerAdminStaffToServer(administrationStaff);

        // Show a success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("The AdministrationStaff has been registered successfully.");
        alert.showAndWait();
    }

    private void registerAdminStaffToServer(AdministrationStaff staff) {
        try (Socket socket = new Socket("localhost", 6789);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject("RegisterAdminStaff");
            oos.writeObject(staff);

            String response = (String) ois.readObject();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    @FXML //select method on combobox
    public void select(ActionEvent event) {
        String s = positionTypeComboBox.getSelectionModel().getSelectedItem().toString();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("FULL_TIME", "PART_TIME", "VOLUNTEER");
        positionTypeComboBox.setItems(list);
    }
    
//    private void saveDataToDatabase(AdministrationStaff staff) {
//        String sql = "INSERT INTO AdministrationStaff (fullName, address, phoneNumber, email, username, password, positionType, staffId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, staff.getFullName());
//            pstmt.setString(2, staff.getAddress());
//            pstmt.setString(3, staff.getPhoneNumber());
//            pstmt.setString(4, staff.getEmail());
//            pstmt.setString(5, staff.getUsername());
//            pstmt.setString(6, staff.getPassword());
//            pstmt.setString(7, staff.getPositionType().name());
//            pstmt.setString(8, staff.getStaffId());
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    private void saveDataToCSV() { //save logic
//        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("staff.csv", true))) {
//            for (AdministrationStaff staff : staffList) {
//                writer.write("Administration Staff : " + "Full Name: " + staff.getFullName() + ","
//                        + staff.getUsername() + ","
//                        + staff.getPassword() + ","
//                        + "Email: " + staff.getEmail() + ","
//                        + "Phone No: " + staff.getPhoneNumber() + ","
//                        + "Address: " + staff.getAddress() + ","
//                        + "Position: " + staff.getPositionType() + ","
//                        + "StaffId: " + staff.getStaffId());
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
