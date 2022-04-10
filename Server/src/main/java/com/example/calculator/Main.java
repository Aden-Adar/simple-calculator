package com.example.calculator;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Main {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new RequestProcess(clientSocket).run();
            }
        } catch (IOException e) {
        }
    }
}