package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientApp {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 5000;

        try {

            Socket socket = new Socket(host, port);

            System.out.println("Connected to Server.");

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true
            );

            System.out.println(input.readLine());

            output.println("Hello Server");

            System.out.println(input.readLine());

            socket.close();

        } catch (IOException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

    }

}