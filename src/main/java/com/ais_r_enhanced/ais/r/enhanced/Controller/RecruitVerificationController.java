/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.ComputeClient;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ais_r_enhanced.ais.r.enhanced.ComputeClient.sendRequest;


/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class RecruitVerificationController implements Initializable {

    RecruitDetails recruitDetails;
    /**
     * Initializes the controller class.
     */
    @FXML
    public Label Label1Id;
    @FXML
    public Button allocatebtn;
    @FXML
    public TextField searchText;
    @FXML
    public Button searchbtn;
    @FXML
    public ComboBox<RecruitDetails.Department> departmentCombo;
//    private RecruitService recruitService = new RecruitService();

    @FXML
    public void allocate(ActionEvent event) {
        // Get the selected department from the ComboBox
        RecruitDetails.Department selectedDepartment = departmentCombo.getValue();
 

        // Update the selected department
        recruitDetails.setDepartment(selectedDepartment);
        sendRequest("updateRecruitDetails",recruitDetails);
        // Show a success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Department Allocated");
        alert.setContentText("Department allocation is successfull.");
        alert.showAndWait();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<RecruitDetails.Department> list = FXCollections.observableArrayList(RecruitDetails.Department.values());
        departmentCombo.setItems(list);
        departmentCombo.setDisable(true);
        allocatebtn.setDisable(true);
    }

    @FXML
    public void search(ActionEvent event) {
        String fullName = searchText.getText().trim();
        if (!fullName.isEmpty()) {
            searchRecruit(fullName);
        }
    }

    @FXML
    void select(ActionEvent event) {
        String s = departmentCombo.getSelectionModel().getSelectedItem().toString();
        allocatebtn.setDisable(false);
    }
//logic for seach

    private void searchRecruit(String fullName) {
        recruitDetails = (RecruitDetails) ComputeClient.sendRequest("findRecruitDetailsByFullName",fullName);
        if (recruitDetails != null) {
            Label1Id.setText(recruitDetails.generateDetailsString());
            departmentCombo.setDisable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User Not Found");
            alert.show();
            Label1Id.setText("Recruit not found");
        }


    }

}
