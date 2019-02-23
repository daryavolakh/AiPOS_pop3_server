package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Darya and Alesya
 */
public class Server {

    static int port;
    public boolean isRunning;
    public ServerSocket serverSocket;
    public DataBase db;

    public Server(int port) {
        this.port = port;
       // db = new DataBase();
       // List<String> list = db.getInfo();
      //  System.out.println("LIST: " + list);
    }

    public void quit() {
        System.out.println("QUIT");
        isRunning = false;
        try {
            serverSocket.close();
        } catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    public void start() {
        isRunning = true;

        try {
            serverSocket = new ServerSocket(port);

            int numOfClients = 1;
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Spawning " + numOfClients);

                Runnable r = new ServerHandler(client, Server.this);
                Thread thread = new Thread(r);
                thread.start();

                numOfClients++;
            }
        } catch (IOException exeption) {
            System.out.println("Something went wrong 1");
        }
    }

    public static void main(String[] args) {
        int port = 110;
        Server server = new Server(port);
        server.start();
    }
}
