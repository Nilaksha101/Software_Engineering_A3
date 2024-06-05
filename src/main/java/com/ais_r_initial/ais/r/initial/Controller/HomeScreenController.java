/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ais_r_initial.ais.r.initial.Controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author pabit
 */
public class HomeScreenController {
    @FXML
    public final BooleanProperty showRecruit = new SimpleBooleanProperty(false);
    @FXML
    public Button adminStaff;
    @FXML
    public Button managementstaff;
    @FXML
    public Button recruit;
    @FXML
    public Button exitButton;
    private Stage loginStage;

    public HomeScreenController(Stage loginStage) {
        this.loginStage = loginStage;
    }

    @FXML
    public void initialize() {
        showRecruit.set(false);
        recruit.setVisible(false);
    }

//    @FXML
//    private void handleAdminStaffButton(ActionEvent event) {
//        // Load the FXML file for the new page
//        loadPage("AdminPage.fxml");
//    }
//
//    @FXML
//    private void handleManagementStaffButton(ActionEvent event) {
//        loadPage("ManagementPage.fxml");
//    }
//
//    @FXML
//    private void handleRecruitButton(ActionEvent event) {
//        loadPage("RecruitPage.fxml");
//    }

    @FXML
    public void handleExitButtonClicked(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

//    private void loadPage(String fxmlFileName) {
//        try {
//            // Load the FXML file for the new page
//            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
//            Parent root = fxmlLoader.load();
//
//            Object controller = fxmlLoader.getController();
//
//
//            // Get the current stage
//            Stage stage = (Stage) adminStaff.getScene().getWindow();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
