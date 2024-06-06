/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.ComputeClient;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDetails;
import com.ais_r_enhanced.ais.r.enhanced.Utility.Encryptor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static com.ais_r_enhanced.ais.r.enhanced.ComputeClient.sendRequest;


/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class UpdateRecruitDetailsController implements Initializable {

    @FXML
    public Label label1;

    @FXML
    public ComboBox<String> QualificationComboBox;
    @FXML
    public ComboBox<String> LocationComboBox;
    @FXML
    public TextField addressField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField fullNameField;

    @FXML
    public DatePicker interviewDate;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField phoneNumberField;

    @FXML
    public TextField searchText;

    @FXML
    public TextField experience;
    @FXML
    public TextField timeToCompleteProject;

    @FXML
    public Button searchbtn;

    @FXML
    public Button updatebtn;

    private int id;
    private RecruitDetails.Department department;
    private Encryptor encryptor;

    @FXML
    public TextField usernameField;
    private List<RecruitDetails> staffList = new ArrayList<>();

    @FXML
    public void datePicker(ActionEvent event) {
        interviewDate.getValue().toString();
    }

    @FXML
    public void search(ActionEvent event) throws Exception {
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
    public void update(ActionEvent event) throws Exception {
        // Get the updated details from the text fields
        String fullName = fullNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        LocalDate interviewDateValue = interviewDate.getValue();
        String qualificationLevel = QualificationComboBox.getValue();
        String location = LocationComboBox.getValue();

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
//        delete();//calling delete method
        add();//calling adding method
        // Show a success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Update Successful");
        alert.setContentText("The Recruit member has been updated successfully.");
        alert.showAndWait();

    }

    public void searchRecruit(String fullName) throws Exception {
        RecruitDetails recruitDetails = (RecruitDetails) ComputeClient.sendRequest("findRecruitDetailsByFullName",fullName);
        if(recruitDetails!=null){
            fullNameField.setText(recruitDetails.getFullName());
            addressField.setText(recruitDetails.getAddress());
            phoneNumberField.setText(recruitDetails.getPhoneNumber());
            emailField.setText(recruitDetails.getEmail());
            usernameField.setText(recruitDetails.getUsername());
            passwordField.setText(encryptor.decryptText(recruitDetails.getPassword()));
            LocalDate interviewLocalDate = LocalDate.parse(recruitDetails.getInterviewDate().toString());
            interviewDate.setValue(interviewLocalDate);
            // Set the qualification level in the ComboBox
            String qualificationLevel = recruitDetails.getQualificationLevel().toString();
            QualificationComboBox.setValue(qualificationLevel);
            String location = recruitDetails.getLocation().toString();
            LocationComboBox.setValue(location);
            experience.setText(recruitDetails.getExperience());
            timeToCompleteProject.setText(recruitDetails.getTimeToLeadAndCompleteProject());
            id = recruitDetails.getId();
            department = recruitDetails.getDepartment();
        }else{
            // If the loop finishes without finding the recruit
            System.out.println("Recruit not found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User Not Found");
            alert.show();
        }

    }

    private void delete() {
        // Get the full name
        String fullName = searchText.getText().trim();

        // Create a list to store the modified lines
        List<String> modifiedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("recruit.csv"))) {
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
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("recruit.csv"))) {
                for (String modifiedLine : modifiedLines) {
                    writer.write(modifiedLine);
                    writer.newLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void add() throws Exception {
        LocalDate localDate = interviewDate.getValue();
        Date date = java.sql.Date.valueOf(localDate);

        RecruitDetails details = new RecruitDetails(fullNameField.getText(), addressField.getText(), phoneNumberField.getText(), emailField.getText(), usernameField.getText(), encryptor.encryptText(passwordField.getText()), date, RecruitDetails.QualificationLevel.valueOf(QualificationComboBox.getValue()),
                department,experience.getText(),timeToCompleteProject.getText(),RecruitDetails.Location.valueOf(LocationComboBox.getValue()));
        details.setId(id);
        // Add the Staff object to the list
        staffList.add(details);
        sendRequest("updateRecruitDetails",details);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            encryptor = new Encryptor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> list = FXCollections.observableArrayList("BACHELORS", "MASTERS", " PHD");
        QualificationComboBox.setItems(list);
        LocationComboBox.setItems(FXCollections.observableArrayList("MELBOURNE","SYDNEY","BRISBANE","ADELAIDE"));
    }
    public void disableSearch(){
        searchbtn.setDisable(true);
    }

}
