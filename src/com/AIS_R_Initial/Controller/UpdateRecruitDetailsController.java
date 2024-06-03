/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.AIS_R_Initial.Controller;

import com.AIS_R_Initial.Model.AdministrationStaff;
import com.AIS_R_Initial.Model.RecruitDetails;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class UpdateRecruitDetailsController implements Initializable {

    @FXML
    private Label label1;

    @FXML
    private ComboBox<String> QualificationComboBox;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullNameField;

    @FXML
    private DatePicker interviewDate;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField searchText;

    @FXML
    private Button searchbtn;

    @FXML
    private Button updatebtn;

    @FXML
    private TextField usernameField;

    @FXML
    public void datePicker(ActionEvent event) {
        interviewDate.getValue().toString();
    }

    @FXML
    public void search(ActionEvent event) {
        String fullName = searchText.getText().trim();
        if (!fullName.isEmpty()) {
            searchRecruit(fullName);
        }
    }

    @FXML
    public void select(ActionEvent event) {
        String s = QualificationComboBox.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    public void update(ActionEvent event) {
        // Get the updated details from the text fields
        String fullName = fullNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        LocalDate interviewDateValue = interviewDate.getValue();
        String qualificationLevel = QualificationComboBox.getValue();

        // Check if any required field is empty
        if (fullNameField.getText().isEmpty() || addressField.getText().isEmpty()
                || phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty()
                || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
                || interviewDateValue == null
                || qualificationLevel == null) {
            // Show an error alert if any required field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incomplete Information");
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        if (fullName.matches(".*\\d.*")) {
            // Show an error alert if full name contains digits
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Full Name");
            alert.setContentText("Full Name cannot contain digits.");
            alert.showAndWait();
            return;
        }
        // Check if phone number contains only digits and has exactly 10 digits
        if (!phoneNumber.matches("\\d{10}")) {
            // Show an error alert if phone number is invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Phone Number");
            alert.setContentText("Phone Number must contain exactly 10 digits and no letters.");
            alert.showAndWait();
            return;
        }
        // Check if password has at least 3 characters
        if (password.length() < 3) {
            // Show an error alert 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Password");
            alert.setContentText("Password must have at least 3 characters.");
            alert.showAndWait();
            return;
        }

        //email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!(email.matches(emailRegex))) {
            // Show an error alert if email is invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email Address");
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
            return;
        }

        delete();//calling delete method
        add();//calling adding method
        // Show a success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Update Successful");
        alert.setContentText("The Recruit member has been updated successfully.");
        alert.showAndWait();

    }

    private void searchRecruit(String fullName) {
        try ( BufferedReader reader = new BufferedReader(new FileReader("recruit.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 1 && data[0].equalsIgnoreCase(fullName)) {
                    // Found the recruit and settin them to corosponding fields
                    fullNameField.setText(data[0]);
                    addressField.setText(data[5]);
                    phoneNumberField.setText(data[4]);
                    emailField.setText(data[3]);
                    usernameField.setText(data[1]);
                    passwordField.setText(data[2]);
                    LocalDate interviewLocalDate = LocalDate.parse(data[6]);
                    interviewDate.setValue(interviewLocalDate);
                    // Set the qualification level in the ComboBox
                    String qualificationLevel = data[7];
                    QualificationComboBox.setValue(qualificationLevel);

                    return;
                }
            }
            // If the loop finishes without finding the recruit
            System.out.println("Recruit not found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User Not Found");
            alert.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void delete() {
        // Get the full name 
        String fullName = searchText.getText().trim();

        // Create a list to store the modified lines
        List<String> modifiedLines = new ArrayList<>();

        try ( BufferedReader reader = new BufferedReader(new FileReader("recruit.csv"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Check if the line contains the recruit's full name
                if (data.length >= 1 && data[0].equalsIgnoreCase(fullName)) {
                    found = true;
                    continue;
                }
                modifiedLines.add(line);
            }

            // Write the modified lines to the file
            try ( BufferedWriter writer = new BufferedWriter(new FileWriter("recruit.csv"))) {
                for (String modifiedLine : modifiedLines) {
                    writer.write(modifiedLine);
                    writer.newLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void saveDataToCSV() {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("recruit.csv", true))) {
            for (RecruitDetails staff : staffList) {
                writer.write(staff.getFullName() + ","
                        + staff.getUsername() + ","
                        + staff.getPassword() + ","
                        + staff.getEmail() + ","
                        + staff.getPhoneNumber() + ","
                        + staff.getAddress() + ","
                        + staff.getInterviewDate() + ","
                        + staff.getQualificationLevel());

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<RecruitDetails> staffList = new ArrayList<>();

    private void add() {
        LocalDate localDate = interviewDate.getValue();
        Date date = java.sql.Date.valueOf(localDate);

        RecruitDetails details = new RecruitDetails(fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), date, RecruitDetails.QualificationLevel.valueOf(QualificationComboBox.getValue()));

        // Add the Staff object to the list
        staffList.add(details);
        // Save the data to the CSV file
        saveDataToCSV();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("BACHELORS", "MASTERS", " PHD");
        QualificationComboBox.setItems(list);
    }

}
