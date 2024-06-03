/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.AIS_R_Initial.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class LoginFxmlController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if username and password are correct
        if (isValid(username, password)) {
            // Get the login stage
            Stage loginStage = (Stage) usernameField.getScene().getWindow();

            // navigate to the dashboard if admin user login
            if (username.startsWith("A") || username.startsWith("a")) {
                navigateToDashboard(loginStage, username);
            }
            // navigate to the dashboard if managementstaff user login
            if (username.startsWith("M") || username.startsWith("m")) {
                navigateToManagementDashboard(loginStage);
            }

        } else {
            // Show error message if login is unsuccessful
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please check Credential");
            alert.show();

        }
    }

    // Method to check if the username and password are valid
    private boolean isValid(String username, String password) {

        // Check if username and password match the default admin credentials.Please note that this is an entry point specific to admin
        if (username.equals("admin") && password.equals("admin")) {
            return true;
        }

        // Read usernames and passwords from the "recruit.csv" file
        try ( BufferedReader br = new BufferedReader(new FileReader("staff.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[1];
                String storedPassword = parts[2];
                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    //navigate to admin dashboard
    private void navigateToDashboard(Stage loginStage, String username) {
        DashboardFxmlController dashboard = new DashboardFxmlController(loginStage, username);
        dashboard.show(); // Method to show the dashboard
        closeLoginScene(); // Close the login scene 
    }

    //navigate to management dashboard
    private void navigateToManagementDashboard(Stage loginStage) {
        ManagementDashBoardController dashboard = new ManagementDashBoardController(loginStage);
        dashboard.show(); // Method to show the dashboard
        closeLoginScene(); // Close the login scene 
    }

    private void closeLoginScene() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
