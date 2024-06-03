/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.AIS_R_Initial.Controller;

import com.AIS_R_Initial.Model.AdministrationStaff;
import com.AIS_R_Initial.Model.RecruitDetails;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class RecruitRegistrationController implements Initializable {

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
    private ComboBox<String> QualificationComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private Button registerButton;

    @FXML
    private DatePicker interviewDate;

    private List<RecruitDetails> recruitList = new ArrayList<>();

    @FXML
    public void Register(ActionEvent event) {
// Check if any required field is empty
        if (fullNameField.getText().isEmpty() || addressField.getText().isEmpty()
                || phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty()
                || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
                || interviewDate.getValue() == null || QualificationComboBox.getValue() == null) {
            // Show an error alert 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incomplete Information");
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        String name = fullNameField.getText();
        if (name.matches(".*\\d.*")) {
            // Show an error alert if full name contains digits
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
            // Show an error alert if phone number is invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Phone Number");
            alert.setContentText("Phone Number must contain exactly 10 digits and no letters.");
            alert.showAndWait();
            return; // Exit the method if phone number is invalid
        }
        // Check if password has at least 3 characters
        String password = passwordField.getText();
        if (password.length() < 3) {
            // Show an error alert if password is too short
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Password");
            alert.setContentText("Password must have at least 3 characters.");
            alert.showAndWait();
            return; // Exit the method if password is too short
        }

        //email validation
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

        LocalDate localDate = interviewDate.getValue();
        Date date = java.sql.Date.valueOf(localDate);

        RecruitDetails details = new RecruitDetails(fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), date, RecruitDetails.QualificationLevel.valueOf(QualificationComboBox.getValue()));

        // Add the Recruit object to the list
        //recruitList.add(details);
        // Save the data to the CSV file
        //saveDataToCSV();
        // Show a success alert
        
             // Send the Recruit object to the server
       registerRecruitToServer(details);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("The Recruit details has been added successfully.");
        alert.showAndWait();
    }
    
  private void registerRecruitToServer(RecruitDetails recruit) {
     try (Socket socket = new Socket("localhost", 6789);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.writeObject("RegisterRecruitDetails");
            oos.writeObject(recruit);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    @FXML
    public void datePicker(ActionEvent event) {
        interviewDate.getValue().toString();
    }

    @FXML
    void select(ActionEvent event) {
        String s = QualificationComboBox.getSelectionModel().getSelectedItem().toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("BACHELORS", "MASTERS", " PHD");
        QualificationComboBox.setItems(list);
    }
//
//    private void saveDataToCSV() {
//        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("recruit.csv", true))) {
//            for (RecruitDetails staff : recruitList) {
//                writer.write(staff.getFullName() + ","
//                        + staff.getUsername() + ","
//                        + staff.getPassword() + ","
//                        + staff.getEmail() + ","
//                        + staff.getPhoneNumber() + ","
//                        + staff.getAddress() + ","
//                        + staff.getInterviewDate() + ","
//                        + staff.getQualificationLevel());
//
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
