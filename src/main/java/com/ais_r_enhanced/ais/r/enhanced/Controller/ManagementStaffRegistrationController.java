/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.ComputeClient;
import com.ais_r_enhanced.ais.r.enhanced.Model.ManagementStaff;
import com.ais_r_enhanced.ais.r.enhanced.Utility.Encryptor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class ManagementStaffRegistrationController implements Initializable {

    @FXML
    public TextField addressField;

    @FXML
    public ComboBox<String> branchComboBox1;

    @FXML
    public TextField emailField;

    @FXML
    public TextField fullNameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField phoneNumberField;

    @FXML
    public Button registerButton;

    @FXML
    public ComboBox<String> roleTypeComboBox;

    @FXML
    public TextField staffIdField;

    @FXML
    public TextField username1Field;

    private List<ManagementStaff> staffList = new ArrayList<>();

    @FXML
    public void Register(ActionEvent event) throws Exception {

        // Check if any required field is empty
        if (fullNameField.getText().isEmpty() || addressField.getText().isEmpty()
                || phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty()
                || username1Field.getText().isEmpty() || passwordField.getText().isEmpty()
                || staffIdField.getText().isEmpty() || roleTypeComboBox.getValue() == null || branchComboBox1.getValue() == null) {
            // Show an error alert if any required field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incomplete Information");
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        // Check if the username starts with 'M' or 'm'
        String username = username1Field.getText();
        String name = fullNameField.getText();
        if (name.matches(".*\\d.*")) {
            // Show an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Full Name");
            alert.setContentText("Full Name cannot contain digits.");
            alert.showAndWait();
            return;
        }
        // Check if phone number contains only digits and has exactly 10 digits
        String phoneNumber = phoneNumberField.getText();
        if (!phoneNumber.matches("\\d{10}")) {
            // Show an error alert 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Phone Number");
            alert.setContentText("Phone Number must contain exactly 10 digits and no letters.");
            alert.showAndWait();
            return;
        }
        // Check if password has at least 3 characters
        String password = passwordField.getText();
        if (password.length() < 3) {
            // Show an error alert 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Password");
            alert.setContentText("Password must have at least 3 characters.");
            alert.showAndWait();
            return;
        }

        //email verification
        String email = emailField.getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!(email.matches(emailRegex))) {
            // Show an error alert if email is invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email Address");
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
            return; // Exit the method if email is invalid
        }
        Encryptor encryptor = new Encryptor();
        String pass = encryptor.encryptText(passwordField.getText());
        ManagementStaff managementStaff = new ManagementStaff(fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), username1Field.getText(), pass, staffIdField.getText(), ManagementStaff.ManagementLevel.valueOf(roleTypeComboBox.getValue()), ManagementStaff.Branch.valueOf(branchComboBox1.getValue()));

        // Add the Staff object to the list
        staffList.add(managementStaff);
        ComputeClient.sendRequest("ManagementRegistration",managementStaff);

        // Show a success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("The ManagementStaff has been registered successfully.");
        alert.showAndWait();
    }

    //get selected item from drop-down
    @FXML
    void select(ActionEvent event) {
        String r = roleTypeComboBox.getSelectionModel().getSelectedItem();
        String b = branchComboBox1.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("SENIOR_MANAGER", "MID_LEVEL_MANAGER", "SUPERVISOR");
        roleTypeComboBox.setItems(list);
        ObservableList<String> branchlist = FXCollections.observableArrayList("MELBOURNE", "SYDNEY", "BRISBANE", "ADELAIDE");
        branchComboBox1.setItems(branchlist);
    }
}
