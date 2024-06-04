package ComputeServer;


import com.AIS_R_Initial.Controller.ClientHandler;
import com.AIS_R_Initial.Model.AdministrationStaff;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author 12217608
 */
public class ComputeServer implements Runnable {

    public static void main(String[] args) {
        DatabaseSetup.setupDatabase();
        ComputeServer computeServer = new ComputeServer();
        Thread serverThread = new Thread(computeServer);
        serverThread.start();
    }

    @Override
    public void run() {
        startServer();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(6789)) {
            System.out.println("Server started listening on port 6789. Waiting for connections...");

            boolean isRunning = true;

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

