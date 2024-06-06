/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author 12217608
 */
public class ComputeClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1212;

    public static Object sendRequest(String action, Object... params) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeUTF(action);
            for (Object param : params) {
                output.writeObject(param);
            }
            output.flush();

            return input.readObject();  

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
