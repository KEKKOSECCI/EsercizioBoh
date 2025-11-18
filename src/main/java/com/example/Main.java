package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static String[] zone = {"a","b","c","d"};
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        String pin = "05057";
        boolean allarme =false;
            System.out.println("stiamo cercando un Client");
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            //facciamo finta che questi messaggi stampati a terminale che servano a me per fare debug poi diventino scritture in file di log per memorizzare le azioni del nostro server
            System.out.println("Client trovato");
            do {
                String get = in.readLine();
                String[] getAr = get.split("_");
                if(!getAr[0].equals("05057")||controlloValidità(getAr[1]) < 0){
                    out.println("ERROR");
                }else if(controlloValidità(getAr[1]) !=2){
                    if(controlloPresenza(getAr[2]))
                    {
                        out.println("ERROR");
                    }
                }else if(controlloValidità(getAr[1])== 0){
                    int flag = statusGroup(getAr[2]);
                    if(flag == 0)
                    out.println("ALREADY");
                    else if(flag == 1)
                    out.println("ON");
                    
                }
            } while (true);
    }
    public static boolean controlloPresenza(String a){
        for(String n : zone){
            if(n.equalsIgnoreCase(a)){
                return true;
            }
        }
        return false;
    }
    public static int controlloValidità(String a){
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
    public static void delAll(){
        for(String n : zone){
            n = n.toLowerCase();
        }
    }
    public static int statusGroup(String a){
        for(String n : zone){
            if(n.equals(a)){
                return 0;
            }else if(n.equalsIgnoreCase(a)){
                n = n.toUpperCase();
                return 1;
            }
        }
        return -1;//errore ma si controlla prima quindi impossibile;
    }
}