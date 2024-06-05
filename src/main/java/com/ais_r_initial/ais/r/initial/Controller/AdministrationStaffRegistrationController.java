/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_initial.ais.r.initial.Controller;

import com.ais_r_initial.ais.r.initial.Model.AdministrationStaff;
import com.ais_r_initial.ais.r.initial.Utility.Encryptor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.ais_r_initial.ais.r.initial.ComputeClient.sendRequest;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class AdministrationStaffRegistrationController implements Initializable {

    @FXML
    public TextField addressField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField fullNameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField phoneNumberField;

    @FXML
    public ComboBox<String> positionTypeComboBox;

    @FXML
    public TextField staffIdField;

    @FXML
    public TextField usernameField;

    @FXML
    public Button registerButton;

    private List<AdministrationStaff> staffList = new ArrayList<>();//list where data is saved

    @FXML
    public void Register(ActionEvent event) throws Exception {

        // Check if any required fields are empty
        if (fullNameField.getText().isEmpty() || addressField.getText().isEmpty()
                || phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty()
                || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
                || staffIdField.getText().isEmpty() || positionTypeComboBox.getValue() == null) {
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
        Encryptor encryptor = new Encryptor();
        String psw = encryptor.encryptText(passwordField.getText());
        AdministrationStaff administrationStaff = new AdministrationStaff(fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), usernameField.getText(), psw, staffIdField.getText(), AdministrationStaff.PositionType.valueOf(positionTypeComboBox.getValue()));
        // Add the Staff object to the list
        staffList.add(administrationStaff);
        sendRequest("AdminRegistration",administrationStaff);
//        insertAdminitrationStaff(administrationStaff);
        // Save the data to the file
//        saveDataToCSV();

        // Show a success alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("The AdministrationStaff has been registered successfully.");
        alert.showAndWait();
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

    private void saveDataToCSV() { //save logic
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("staff.csv", true))) {
            for (AdministrationStaff staff : staffList) {
                writer.write("Administration Staff : " + "Full Name: " + staff.getFullName() + ","
                        + staff.getUsername() + ","
                        + staff.getPassword() + ","
                        + "Email: " + staff.getEmail() + ","
                        + "Phone No: " + staff.getPhoneNumber() + ","
                        + "Address: " + staff.getAddress() + ","
                        + "Position: " + staff.getPositionType() + ","
                        + "StaffId: " + staff.getStaffId());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
