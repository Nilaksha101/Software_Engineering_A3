/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.AIS_R_Initial.Controller;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
import javafx.scene.control.Alert.AlertType;
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
//public class RecruitVerificationController implements Initializable {
//
//    /**
//     * Initializes the controller class.
//     */
//    @FXML
//    private Label Label1Id;
//
//    @FXML
//    private Button allocatebtn;
//
//    @FXML
//    private TextField searchText;
//
//    @FXML
//    private Button searchbtn;
//
//    @FXML
//    private ComboBox<String> departmentCombo;
//
//  
//@FXML
//public void allocate(ActionEvent event) {
//     String selectedDepartment = departmentCombo.getValue();
//    String recruitFullName = Label1Id.getText().trim();
//
//    try (Socket socket = new Socket("localhost", 6789);
//         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//         ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
//
//        oos.writeObject("UpdateDepartment");
//        oos.writeObject(id);  // Send recruitId instead of fullName
//        oos.writeObject(selectedDepartment);
//
//        String response = (String) ois.readObject();
//
//        Alert alert = new Alert(AlertType.INFORMATION);
//        if (response.equals("Success")) {
//            alert.setTitle("Success");
//            alert.setHeaderText("Department Allocated");
//            alert.setContentText("Department allocation is successful.");
//        } else {
//            alert.setTitle("Error");
//            alert.setHeaderText("Allocation Failed");
//            alert.setContentText(response);
//        }
//        alert.showAndWait();
//
//    } catch (IOException | ClassNotFoundException e) {
//        e.printStackTrace();
//        System.err.println("Error updating recruit data with department allocation: " + e.getMessage());
//    }
//    
//    
////    String selectedDepartment = departmentCombo.getValue();
////    String recruitFullName = Label1Id.getText().trim();
////
////    //System.out.println("Selected Department: " + selectedDepartment);
////    //System.out.println("Recruit Full Name: " + recruitFullName);
////
////    try (Socket socket = new Socket("localhost", 6789);
////         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
////         ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
////
////        oos.writeObject("UpdateDepartment");
////        oos.writeObject(recruitFullName);
////        oos.writeObject(selectedDepartment);
////
////        String response = (String) ois.readObject();
////
////        System.out.println("Response from server: " + response);
////
////        Alert alert = new Alert(AlertType.INFORMATION);
////        if (response.equals("Success")) {
////            alert.setTitle("Success");
////            alert.setHeaderText("Department Allocated");
////            alert.setContentText("Department allocation is successful.");
////        } else {
////            alert.setTitle("Error");
////            alert.setHeaderText("Allocation Failed");
////            alert.setContentText(response);
////        }
////        alert.showAndWait();
////
////    } catch (IOException | ClassNotFoundException e) {
////        e.printStackTrace();
////        System.err.println("Error updating recruit data with department allocation: " + e.getMessage());
////    }
//}
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        ObservableList<String> list = FXCollections.observableArrayList("Mechanical Engineering", "Software Engineering", "Aerospace Engineering", "Electronic Engineering");
//        departmentCombo.setItems(list);
//        departmentCombo.setDisable(true);
//        allocatebtn.setDisable(true);
//    }
//
//    @FXML
//    public void search(ActionEvent event) {
//        String fullName = searchText.getText().trim();
//        if (!fullName.isEmpty()) {
//            searchRecruit(fullName);
//        }
//    }
//
//    @FXML
//    void select(ActionEvent event) {
//        String s = departmentCombo.getSelectionModel().getSelectedItem().toString();
//        allocatebtn.setDisable(false);
//    }
////logic for seach
//
//private void searchRecruit(String fullName) {
//        try (Socket socket = new Socket("localhost", 6789);
//             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
//
//            oos.writeObject("SearchRecruit");
//            oos.writeObject(fullName);
//
//            String searchResult = (String) ois.readObject();
//
//            if (!"Recruit not found".equals(searchResult)) {
//                Label1Id.setText(searchResult);
//                departmentCombo.setDisable(false);
//                Alert alert = new Alert(AlertType.CONFIRMATION);
//                alert.setTitle("Verified");
//                alert.setHeaderText("User Found");
//                alert.setContentText("Click ok to verify.");
//                alert.showAndWait();
//            } else {
//                Label1Id.setText("Recruit not found");
//                Alert alert = new Alert(AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("User Not Found");
//                alert.show();
//            }
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            Label1Id.setText("Error reading recruit data");
//        }
//    }
//
//}

public class RecruitVerificationController implements Initializable {

    private int recruitId; // Add this variable to store recruitId

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
        allocatebtn.setDisable(false);
    }

    @FXML
    public void allocate(ActionEvent event) {
        String selectedDepartment = departmentCombo.getValue();
        if (selectedDepartment != null && recruitId != 0) {
            try (Socket socket = new Socket("localhost", 6789);
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Sending update department request with recruitId: " + recruitId + " and department: " + selectedDepartment);

                oos.writeObject("UpdateDepartment");
                oos.writeObject(recruitId);  // Send recruitId
                oos.writeObject(selectedDepartment);

                String response = (String) ois.readObject();

                System.out.println("Response from server: " + response);

                Alert alert = new Alert(AlertType.INFORMATION);
                if ("Success".equals(response)) {
                    alert.setTitle("Success");
                    alert.setHeaderText("Department Allocated");
                    alert.setContentText("Department allocation is successful.");
                } else {
                    alert.setTitle("Error");
                    alert.setHeaderText("Allocation Failed");
                    alert.setContentText(response);
                }
                alert.showAndWait();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error updating recruit data with department allocation: " + e.getMessage());
            }
        }
    }

    private void searchRecruit(String fullName) {
        try (Socket socket = new Socket("localhost", 6789);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject("SearchRecruit");
            oos.writeObject(fullName);

            String searchResult = (String) ois.readObject();

            System.out.println("Search result: " + searchResult);

            if (!"Recruit not found".equals(searchResult)) {
                // Extract the id from the search result and store it in recruitId
                String[] details = searchResult.split("\n");
                for (String detail : details) {
                    if (detail.startsWith("Id : ")) { // Note the space after "Id"
                        recruitId = Integer.parseInt(detail.substring(5).trim()); // Update the starting index to 5
                        break;
                    }
                }
                System.out.println("Extracted recruitId: " + recruitId);
                Label1Id.setText(searchResult);
                departmentCombo.setDisable(false);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Verified");
                alert.setHeaderText("User Found");
                alert.setContentText("Click ok to verify.");
                alert.showAndWait();
            } else {
                Label1Id.setText("Recruit not found");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User Not Found");
                alert.show();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Label1Id.setText("Error reading recruit data");
        }
    }
}