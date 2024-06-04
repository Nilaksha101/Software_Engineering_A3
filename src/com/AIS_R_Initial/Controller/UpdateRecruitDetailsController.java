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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
             sendSearchRequest(fullName);
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
        
        // Create a new RecruitDetails object with updated values
        Date date = java.sql.Date.valueOf(interviewDateValue);
        RecruitDetails recruit = new RecruitDetails(fullName, address, phoneNumber, email, username, password, date, RecruitDetails.QualificationLevel.valueOf(qualificationLevel));

        // Send update request to the server
        sendUpdateRequest(recruit);
        
    }
      private void sendSearchRequest(String fullName) {
        try (Socket socket = new Socket("localhost", 6789);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject("SearchRecruit");
            oos.writeObject(fullName);

            String searchResult = (String) ois.readObject();
            updateUI(searchResult);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to communicate with the server.");
        }
    }

      
   private void updateUI(String searchResult) {
    if (searchResult.equals("Recruit not found")) {
        showAlert(Alert.AlertType.ERROR, "Error", "Recruit not found.");
        clearFields();
    } else {
        String[] data = searchResult.split("\n");
        fullNameField.setText(data[0].split(": ")[1]);
        fullNameField.setDisable(true);
        addressField.setText(data[1].split(": ")[1]);
        phoneNumberField.setText(data[2].split(": ")[1]);
        emailField.setText(data[3].split(": ")[1]);
        usernameField.setText(data[4].split(": ")[1]);
        passwordField.setText(data[4].split(": ")[1]);

        // Handle date parsing
        try {
            LocalDate interviewLocalDate = LocalDate.parse(data[5].split(": ")[1]);
            interviewDate.setValue(interviewLocalDate);
        } catch (DateTimeParseException e) {
            interviewDate.setValue(null); 
        }

        // Set the qualification level in the ComboBox
        try {
            String qualificationLevel = data[6].split(": ")[1];
            QualificationComboBox.setValue(qualificationLevel);
        } catch (ArrayIndexOutOfBoundsException e) {
            QualificationComboBox.setValue(null); 
        }

        updatebtn.setDisable(false);
    }
}


    private void sendUpdateRequest(RecruitDetails recruit) {
        try (Socket socket = new Socket("localhost", 6789);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject("UpdateRecruit");
            oos.writeObject(recruit);

            String updateResult = (String) ois.readObject();
            showAlert(Alert.AlertType.INFORMATION, "Update", updateResult);
            
            if ("Update successful".equals(updateResult)) {
            clearFields();
        }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to communicate with the server.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        fullNameField.clear();
        addressField.clear();
        phoneNumberField.clear();
        emailField.clear();
        usernameField.clear();
        passwordField.clear();
        interviewDate.setValue(null);
        QualificationComboBox.setValue(null);
        updatebtn.setDisable(true);
    }  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("BACHELORS", "MASTERS", " PHD");
        QualificationComboBox.setItems(list);
    }

}
