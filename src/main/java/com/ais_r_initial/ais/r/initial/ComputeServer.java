/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.ais_r_initial.ais.r.initial;


import com.ais_r_initial.ais.r.initial.Model.DatabaseConnector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author 12217608
 */
public class ComputeServer {

    private static final int PORT = 1212;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            DatabaseConnector.initializeDatabase();
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                threadPool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
            ClientHandler.closeDatabaseConnection();
        }
    }
}
