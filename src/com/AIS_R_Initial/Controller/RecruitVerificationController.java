/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.AIS_R_Initial.Controller;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class RecruitVerificationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label Label1Id;

    @FXML
    private Button allocatebtn;

    @FXML
    private TextField searchText;

    @FXML
    private Button searchbtn;

    @FXML
    private ComboBox<String> departmentCombo;

    @FXML
    public void allocate(ActionEvent event) {
        // Get the selected department from the ComboBox
        String selectedDepartment = departmentCombo.getValue();
        // Get the recruit's full name from the label
        String recruitFullName = Label1Id.getText().split("\n")[0];

        // Update the recruit.csv file with the selected department
        try {
            // Read the existing lines from the file and store them in a list
            List<String> lines = Files.readAllLines(Paths.get("recruit.csv"));

            // Iterate through the lines to find and update the department for the recruit
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(recruitFullName)) {
                    // Update the line with the selected department
                    lines.set(i, line + "," + selectedDepartment);
                    break;
                }
            }

            // Write the updated lines 
            Files.write(Paths.get("recruit.csv"), lines);

            // Show a success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Department Allocated");
            alert.setContentText("Department allocation is successfull.");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error updating recruit data with department allocation");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Mechanical Engineering", "Software Engineering", "Aerospace Engineering", "Electronic Engineering");
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
        try ( BufferedReader reader = new BufferedReader(new FileReader("recruit.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 1 && data[0].equalsIgnoreCase(fullName)) {
                    // Found the recruit, update the Label with details
                    StringBuilder details = new StringBuilder();
                    for (String field : data) {
                        details.append(field).append("\n");
                    }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Verified");
                    alert.setHeaderText("User Found");
                    alert.setContentText("Click ok to verify.");
                    alert.showAndWait();
                    Label1Id.setText(details.toString());
                    // Enable the ComboBox
                    departmentCombo.setDisable(false);
                    return;
                }
            }
            // If no user found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User Not Found");
            alert.show();
            Label1Id.setText("Recruit not found");
        } catch (IOException e) {
            e.printStackTrace();
            Label1Id.setText("Error reading recruit data");
        }
    }

}
