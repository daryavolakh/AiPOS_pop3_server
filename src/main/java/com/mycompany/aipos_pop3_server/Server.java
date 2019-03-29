package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import org.apache.log4j.*;
import org.apache.log4j.Logger;
/**
 *
 * @author Darya and Alesya
 */
public class Server {

    static int port;
    public boolean isRunning;
    public ServerSocket serverSocket;   
    public Logger log = Logger.getLogger(Server.class);

    public Server(int port) {
        this.port = port;
        PropertyConfigurator.configure("log4j.properties");
        
        log.info("Start server");
    }

    public void start() {
        isRunning = true;
        
        try {
            serverSocket = new ServerSocket(port);

            int numOfClients = 0;
            while (true) {
                Socket client = serverSocket.accept();

                Runnable r = new ServerHandler(client, Server.this);
                Thread thread = new Thread(r);
                thread.start();

                numOfClients++;  
                System.out.println("NEW CLIENT");
                //System.out.println("number of clients " + numOfClients);
            }
        } catch (IOException exeption) {
            log.info("Start server error!");
        }
    }

    public static void main(String[] args) {
        /*int port = Integer.parseInt(args[0]);
        System.out.println(port);*/
        int port = 110;
        Server server = new Server(port);
        server.start();
    }
}