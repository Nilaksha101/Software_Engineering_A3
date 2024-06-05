/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_initial.ais.r.initial.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class ManagementDashBoardController implements Initializable {

    private Stage loginStage;

    public ManagementDashBoardController(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public ManagementDashBoardController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void selectRecruitVerification(ActionEvent event) {
        try {
            // Load the FXML file for the verification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_initial/ais/r/initial/RecruitVerification.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("Recruit Verification");

            // Show the stage
            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void viewAllRecruitDetail(ActionEvent event) {
        try {
            // Load the FXML file for the verification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_initial/ais/r/initial/RecruitDetailList.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("Recruit Verification");

            // Show the stage
            registrationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToUpdateRecruit(ActionEvent event) {
        try {
            // Load the FXML file for the recruit update form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_initial/ais/r/initial/updateRecruitDetails.fxml"));
            Parent root = loader.load();

            // Create a new stage for the staff registration form
            Stage registrationStage = new Stage();
            registrationStage.setScene(new Scene(root));
            registrationStage.setTitle("Recruit Update");

            // Show stage
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

    public void setloginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public void show() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_initial/ais/r/initial/ManagementDashBoard.fxml"));
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
            e.printStackTrace();
        }
    }

    public void showprevious() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_initial/ais/r/initial/LoginFxml.fxml"));
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
//            e.printStackTrace();
        }

    }

}
