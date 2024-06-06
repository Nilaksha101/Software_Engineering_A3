/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.Model.AdministrationStaff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class DashboardFxmlController implements Initializable {

    @FXML
    public Button adminStaffId;
    /**
     * Initializes the controller class.
     */
    private AdministrationStaff username;//whee the data is set
    private Stage loginStage;
    @FXML
    public Button managementStaffId;

    // Constructor to accept the login stage
    public DashboardFxmlController(Stage loginStage, String username) {
        this.loginStage = loginStage;
    }

    // Default constructor  
    public DashboardFxmlController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void selectAdministrationRegister(ActionEvent event) {
        try {
            // Load the FXML file for the staff registration form
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
    public void selectManagementRegister(ActionEvent event) {
        try {
            // Load the FXML file for the staff registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/ManagementStaffRegistration.fxml"));
            Parent root = loader.load();

            // Create a new stage for the staff registration form
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("Management Staff Registration");

            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void selectRecruitRegister(ActionEvent event) {
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

    @FXML
    public void navigateToOPTRequest(ActionEvent event) {

        try {
            // Load the FXML file for the staff registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/RecruitDetailOTPRequestList.fxml"));
            Parent root = loader.load();

            // Create a new stage for the staff registration form
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("OTP Request");

            // Show the staff registration form stage
            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exit(ActionEvent event) {
// Close the dashboard stage
        Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dashboardStage.close();
        showprevious();

    }
    // Show the login stage again

    public void showprevious() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/LoginFxml.fxml"));
            Parent root = loader.load();

            // Create the scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();

            // Close the login stage
            loginStage.close();
        } catch (Exception e) {
        }

    }

    public void show() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/DashboardFxml.fxml"));
            Parent root = loader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Set up the new stage
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();

            // Close the login stage
            loginStage.close();
        } catch (Exception e) {
        }

    }


    @FXML
    public void navigateToDeleteRecruit(ActionEvent event) {

        try {
            // Load the FXML file for the staff registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/DeleteRecruitDetails.fxml"));
            Parent root = loader.load();

            // Create a new stage for the staff registration form
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("Delete Recruit");

            // Show the staff registration form stage
            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void navigateToViewAllManagementStaff(ActionEvent event) {

        try {
            // Load the FXML file for the staff registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_enhanced/ais/r/enhanced/ManagementStaffList.fxml"));
            Parent root = loader.load();

            // Create a new stage for the staff registration form
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("List of Management Staffs");

            // Show the staff registration form stage
            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
