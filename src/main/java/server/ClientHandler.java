package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );

            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            output.println("Welcome to Sanad AI Support Assistant!");

            String message;

            while ((message = input.readLine()) != null) {
                System.out.println("Client says: " + message);

                output.println("AI Assistant: I received your message: " + message);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}