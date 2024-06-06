/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced.Controller;

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

    @FXML
    public void handleExitButtonClicked(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


}
