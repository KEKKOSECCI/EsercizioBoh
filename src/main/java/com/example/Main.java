package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static String[] zone = {"a", "b", "c", "d"};

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        String pin = "05057";
        boolean allarme = false;

        System.out.println("Stiamo cercando un Client...");
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Simulazione del debug (in futuro scriverai su file di log)
        System.out.println("Client trovato");

        do {
            String get = in.readLine();
            String[] getAr = get.split(" ");
            System.out.println(getAr[0]);
            System.out.println(getAr[1]);
            System.out.println(getAr[2]);
            if (!getAr[0].equals(pin) || controlloValidità(getAr[1]) < 0) {
                System.out.println("ERRORE : pin non uguae o comando non adatto ");
                out.println("ERROR");
            } else if (controlloValidità(getAr[1]) != 2) {
                if (controlloPresenza(getAr[2])) {
                    out.println("ERROR");
                }
            } else if (getAr[1].equals("g")) {
                int flag = setGroup(getAr[2]);
                if (flag == 0) {
                    out.println("ALREADY");
                } else if (flag == 1) {
                    out.println("ON");
                } else {
                    out.println("ERROR");
                }
            } else if (getAr[1].equals("STATUS")) {
                if (status(getAr[2]) == 0)
                    out.println("ALREADY");
                else if (status(getAr[2]) == 1)
                    out.println("ON");
            } else if (getAr[1].equals("DIS"))
                delAll();
        } while (true);
    }

    public static boolean controlloPresenza(String a) {
        for (String n : zone) {
            if (n.equalsIgnoreCase(a)) {
                return true;
            }
        }
        return false;
    }

    public static int controlloValidità(String a) {
        switch (a) {
            case "g":
                return 0;
            case "STATUS":
                return 1;
            case "DIS":
                return 2;
            default:
                return -1;
        }
    }

    public static void delAll() {
        for (int i = 0; i < zone.length; i++) {
            zone[i] = zone[i].toLowerCase();
        }
    }

    public static int setGroup(String a) {
        for (int i = 0; i < zone.length; i++) {
            if (zone[i].equals(a)) {
                return 0; // Il gruppo è già presente
            } else if (zone[i].equalsIgnoreCase(a)) {
                zone[i] = zone[i].toUpperCase(); // Modifica l'elemento a maiuscolo
                return 1; // Il gruppo è stato modificato
            }
        }
        return -1; // Errore, gruppo non trovato
    }

    public static int status(String a) {
        for (String n : zone) {
            if (n.equals(a)) {
                return 0; // Il gruppo è già attivo
            } else if (n.equalsIgnoreCase(a)) {
                return 1; // Il gruppo è stato attivato
            }
        }
        return -1; // Errore, gruppo non trovato
    }
}
