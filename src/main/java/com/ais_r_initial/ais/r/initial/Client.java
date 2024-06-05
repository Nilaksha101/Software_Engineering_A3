/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ais_r_initial.ais.r.initial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 12217608
 */
public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ais_r_initial/ais/r/initial/LoginFxml.fxml"));
        Parent root = loader.load();
        // Set up the scene
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
//        DatabaseConnector.initializeDatabase();
    }

    public static void main(String[] args) {
        launch();
    }

}
