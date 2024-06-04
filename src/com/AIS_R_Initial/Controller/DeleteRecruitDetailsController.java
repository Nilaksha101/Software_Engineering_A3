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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
            sendSearchRequest(fullName);
        }
    }
    
     private void sendSearchRequest(String fullName) {
        try (Socket socket = new Socket("localhost", 6789);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // Send search request to server
            oos.writeObject("SearchRecruit");
            oos.writeObject(fullName);

            // Receive search results from server
            String searchResult = (String) ois.readObject();
            updateUI(searchResult);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to communicate with the server.");
        }
    }

    private void updateUI(String searchResult) {
        Label1Id.setText(searchResult);
        if (searchResult.equals("Recruit not found")) {
            deletebtn.setDisable(true);
        } else {
            deletebtn.setDisable(false);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //search logic
//    private void searchRecruit(String fullName) {
//        try ( BufferedReader reader = new BufferedReader(new FileReader("recruit.csv"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(",");
//                if (data.length >= 1 && data[0].equalsIgnoreCase(fullName)) {
//                    // Found the recruit, update the Label with details
//                    StringBuilder details = new StringBuilder();
//                    for (String field : data) {
//                        details.append(field).append("\n");
//                    }
//                    Label1Id.setText(details.toString());
//                    // Enable the delete button
//                    deletebtn.setDisable(false);
//
//                    return; // Exit loop
//                }
//            }
//            Label1Id.setText("Recruit not found");
//        } catch (IOException e) {
//            e.printStackTrace();
//            Label1Id.setText("Error reading recruit data");
//        }
//    }
@FXML
    public void delete(ActionEvent event) {
        String fullName = searchText.getText().trim();
        if (!fullName.isEmpty()) {
            sendDeleteRequest(fullName);
        }
    }

    private void sendDeleteRequest(String fullName) {
        try (Socket socket = new Socket("localhost", 6789);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // Send delete request to server
            oos.writeObject("DeleteRecruit");
            oos.writeObject(fullName);
             oos.flush();

            // Receive delete confirmation from server
            String deleteResult = (String) ois.readObject();
            showAlert(Alert.AlertType.INFORMATION, "Deletion", deleteResult);

            // Update UI after deletion
            if (deleteResult.equals("Recruit deleted successfully")) {
                Label1Id.setText("");
                searchText.clear();
                deletebtn.setDisable(true);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to communicate with the server.");
        }
    }
}
