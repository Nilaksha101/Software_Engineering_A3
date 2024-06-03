/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.AIS_R_Initial.Controller;

import com.AIS_R_Initial.Model.AdministrationStaff;
import com.AIS_R_Initial.Model.ManagementStaff;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class ManagementStaffRegistrationController implements Initializable {

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<String> branchComboBox1;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button registerButton;

    @FXML
    private ComboBox<String> roleTypeComboBox;

    @FXML
    private TextField staffIdField;

    @FXML
    private TextField username1Field;

    private List<ManagementStaff> staffList = new ArrayList<>();

    @FXML
    public void Register(ActionEvent event) {

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
        if (!username.toLowerCase().startsWith("m")) {
            // Show an error alert 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Username");
            alert.setContentText("Username must start with 'M' or 'm'.");
            alert.showAndWait();
            return;
        }
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
       ManagementStaff managementStaff = new ManagementStaff(fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), username1Field.getText(), passwordField.getText(), staffIdField.getText(), ManagementStaff.ManagementLevel.valueOf(roleTypeComboBox.getValue()), ManagementStaff.Branch.valueOf(branchComboBox1.getValue()));

        // Add the Staff object to the list
        staffList.add(managementStaff);

        // Save the data to the server
        saveDataToServer(managementStaff);

        // Show a success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("The ManagementStaff has been registered successfully.");
        alert.showAndWait();
    }
    
    private void saveDataToServer(ManagementStaff staff) {
        try (Socket socket = new Socket("localhost", 6789);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.writeObject("RegisterManagementStaff");
            oos.writeObject(staff);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    
    
    
//save logic

//    private void saveDataToCSV() {
//        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("staff.csv", true))) {
//            for (ManagementStaff staff : staffList) {
//                writer.write("Management Staff: " + staff.getFullName() + ","
//                        + staff.getUsername() + ","
//                        + staff.getPassword() + ","
//                        + "Email: " + staff.getEmail() + ","
//                        + "Phone Number: " + staff.getPhoneNumber() + ","
//                        + "Address: " + staff.getAddress() + ","
//                        + "Role: " + staff.getManagementLevel() + ","
//                        + "Branch: " + staff.getBranch() + ","
//                        + "staffID: " + staff.getStaffId());
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
