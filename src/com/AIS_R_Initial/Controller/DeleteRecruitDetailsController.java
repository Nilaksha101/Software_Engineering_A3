/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.AIS_R_Initial.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 12217608
 */
public class DeleteRecruitDetailsController implements Initializable {

    @FXML
    private Label Label1Id;

    @FXML
    private Button deletebtn;

    @FXML
    private TextField searchText;

    @FXML
    private Button searchbtn;

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
                    Label1Id.setText(details.toString());
                    // Enable the delete button
                    deletebtn.setDisable(false);

                    return; // Exit loop
                }
            }
            Label1Id.setText("Recruit not found");
        } catch (IOException e) {
            e.printStackTrace();
            Label1Id.setText("Error reading recruit data");
        }
    }

    @FXML
    public void delete(ActionEvent event) {
        // Get the full name 
        String fullName = searchText.getText().trim();

        // Create a list to store the modified lines
        List<String> modifiedLines = new ArrayList<>();

        try ( BufferedReader reader = new BufferedReader(new FileReader("recruit.csv"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Check if the line contains full name
                if (data.length >= 1 && data[0].equalsIgnoreCase(fullName)) {

                    found = true;
                    continue;
                }
                modifiedLines.add(line);
            }

            if (!found) {
            } else {
                Label1Id.setText("Recruit deleted successfully");
                // Write the modified lines back to the file
                try ( BufferedWriter writer = new BufferedWriter(new FileWriter("recruit.csv"))) {
                    for (String modifiedLine : modifiedLines) {
                        writer.write(modifiedLine);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Label1Id.setText("Error deleting recruit");
        }

        // Show a success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deletion");
        alert.setHeaderText("Deleted Successful");
        alert.setContentText("The member has been deleted successfully.");
        alert.showAndWait();
    }
}
