package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    public static void main(String[] args) {
        int port = 5000;

        System.out.println("Sanad AI Support Server is starting...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}