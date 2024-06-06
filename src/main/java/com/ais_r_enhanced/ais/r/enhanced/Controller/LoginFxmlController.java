/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.ComputeClient;
import com.ais_r_enhanced.ais.r.enhanced.Model.AdministrationStaff;
import com.ais_r_enhanced.ais.r.enhanced.Model.ManagementStaff;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class LoginFxmlController implements Initializable {


    @FXML
    public ChoiceBox<String> loginAsChoiceBox;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public void login() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String loginAs = loginAsChoiceBox.getValue();
        // Check if username and password are correct
        if (isValid(username, password, loginAs)) {
            // Get the login stage
            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            switch (loginAs) {
                case "Administration" -> {
                    navigateToDashboard(loginStage, username);
                }
                case "Management" -> {
                    navigateToManagementDashboard(loginStage);
                }
                case "Recruit" -> {
                    navigateToRecruitDashboard(loginStage,username);
                }

            }

        } else {
            // Show error message if login is unsuccessful
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please check Credential and login as per required");
            alert.show();

        }
    }


    // Method to check if the username and password are valid
    private boolean isValid(String username, String password, String loginAs) throws Exception {
        if (loginAs == null){
            return false;
        }
        switch (loginAs) {
            case "Administration" -> {
                AdministrationStaff staff = (AdministrationStaff) ComputeClient.sendRequest("AdminLogin",username,password);
                return staff != null;
            }
            case "Management" -> {
                ManagementStaff staff = (ManagementStaff) ComputeClient.sendRequest("ManagementLogin",username,password);
                return staff != null;
            }
            case "Recruit" -> {
                RecruitDetails staff = (RecruitDetails) ComputeClient.sendRequest("RecruitLogin",username,password);
                return staff != null;
            }
            default -> {
                return false;
            }
        }

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
    private void navigateToRecruitDashboard(Stage loginStage,String username) {
        RecruitDashBoardController dashboard = new RecruitDashBoardController(loginStage,username);
//        dashboard.
        closeLoginScene(); // Close the login scene
        dashboard.show(); // Method to show the dashboard
        dashboard.setUsername(username);
    }

    private void closeLoginScene() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Administration", "Management", "Recruit");
        loginAsChoiceBox.setItems(list);
    }

    @FXML
    public void registerManagement(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the recruit registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/ManagementStaffRegistration.fxml"));
            Parent root = loader.load();

            // Create a new stage for the staff registration form
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("Management Staff Registration");

            // Show the staff registration form stage
            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerAdmin(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the recruit registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/AdministrationStaffRegistration.fxml"));
            Parent root = loader.load();

            // Create a new stage for the staff registration form
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("Administration Staff Registration");

            // Show the staff registration form stage
            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerRecruit() {
        try {
            // Load the FXML file for the recruit registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/RecruitRegistration.fxml"));
            Parent root = loader.load();

            // Create a new stage for the staff registration form
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("Recruit Registration");

            // Show the staff registration form stage
            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
