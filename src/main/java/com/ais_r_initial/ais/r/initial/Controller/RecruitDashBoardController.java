/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_initial.ais.r.initial.Controller;

import com.ais_r_initial.ais.r.initial.ComputeClient;
import com.ais_r_initial.ais.r.initial.Model.RecruitDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ais_r_initial.ais.r.initial.ComputeClient.sendRequest;


/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class RecruitDashBoardController implements Initializable {

    @FXML
    public TextField enterOTP;
    RecruitDetails recruitDetails;
    @FXML
    private Stage loginStage;
    private String username;

    public RecruitDashBoardController(Stage loginStage, String username) {
        this.loginStage = loginStage;
        this.username = username;
    }

    public RecruitDashBoardController() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    public void navigateToUpdateRecruit(ActionEvent event) {
        recruitDetails = (RecruitDetails) ComputeClient.sendRequest("findRecruitDetailsByUsername",username);
        if (enterOTP.getText().equals(recruitDetails.getOTPCode())) {

            try {
                // Load the FXML file for the recruit update form
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_initial/ais/r/initial/updateRecruitDetails.fxml"));
                Parent root = loader.load();
                Object controller = loader.getController();

                // Create the scene
                if (controller instanceof UpdateRecruitDetailsController updateRecruitDetailsController) {
                    // Cast the controller to AdminDashboardController

                    // Call the setAdmin method to pass the AdministrationStaff object
                    updateRecruitDetailsController.disableSearch();
                    updateRecruitDetailsController.searchRecruit(username);
                }

                // Create a new stage for the staff registration form
                Stage registrationStage = new Stage();
                registrationStage.setScene(new Scene(root));
                registrationStage.setTitle("Recruit Update");

                // Show stage
                registrationStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid OTP");
            alert.setContentText("OTP is invalid");
            alert.showAndWait();
            return;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_initial/ais/r/initial/RecruitDashBoard.fxml"));
            Parent root = loader.load();

            Object controller = loader.getController();

            // Create the scene
            Scene scene = new Scene(root);
            if (controller instanceof RecruitDashBoardController) {
                // Cast the controller to AdminDashboardController
                RecruitDashBoardController recruitDashBoardController = (RecruitDashBoardController) controller;

                // Call the setAdmin method to pass the AdministrationStaff object
                recruitDashBoardController.setUsername(username);
            }

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

    public void requestForOTP(ActionEvent actionEvent) {
        recruitDetails = (RecruitDetails) ComputeClient.sendRequest("findRecruitDetailsByUsername",username);
        if (recruitDetails==null){
            throw new RuntimeException();
        }
        recruitDetails.setRequestForOTP(true);
        sendRequest("setRequestForOTPInDB",recruitDetails);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("OTP Requested");
        alert.setContentText("OPT has been requested successfully.");
        alert.showAndWait();
    }
}
