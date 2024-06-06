/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.ComputeClient;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ais_r_enhanced.ais.r.enhanced.ComputeClient.sendRequest;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class DeleteRecruitDetailsController implements Initializable {

    @FXML
    public Label Label1Id;

    @FXML
    public Button deletebtn;

    @FXML
    public TextField searchText;

    @FXML
    public Button searchbtn;
    private RecruitDetails recruitDetails;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deletebtn.setDisable(true);
    }

    @FXML //search
    public void search(ActionEvent event) {
        String fullName = searchText.getText().trim();
        if (!fullName.isEmpty()) {
            searchRecruit(fullName);
        }
    }

    //search logic
    private void searchRecruit(String fullName) {
        try {
            recruitDetails = (RecruitDetails) ComputeClient.sendRequest("findRecruitDetailsByFullName", fullName);
            if (recruitDetails != null) {
                Label1Id.setText(recruitDetails.generateDetailsString());
                deletebtn.setDisable(false);

            } else {
                Label1Id.setText("Recruit not found");
            }
        } catch (Exception e) {
            Label1Id.setText("Error reading recruit data");
        }
    }

    @FXML
    public void delete(ActionEvent event) {
        // Get the full name 
        String fullName = searchText.getText().trim();

        // Create a list to store the modified lines
        boolean deleted = (boolean) sendRequest("DeleteRecruitDetail", recruitDetails.getId());
        if (deleted) {
            // Show a success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion");
            alert.setHeaderText("Deleted Successful");
            alert.setContentText("The member has been deleted successfully.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deletion");
            alert.setHeaderText("Deleted Unsuccessful");
            alert.setContentText("The member cannot be deleted.");
            alert.showAndWait();
        }
        deletebtn.setDisable(true);
    }
}
