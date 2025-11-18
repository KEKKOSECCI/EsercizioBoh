package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        do {
            System.out.println("stiamo cercando un Client");
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            //facciamo finta che questi messaggi stampati a terminale che servano a me per fare debug poi diventino scritture in file di log per memorizzare le azioni del nostro server
            System.out.println("Client trovato");
            do {
                String get = in.readLine();
                String[] getAr = get.split("_");
                System.out.println(getAr[0]);
                System.out.println(getAr[1]);
                System.out.println(getAr[2]);
            } while (true);
        } while (true);
    }
}