package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.log4j.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**
 *
 * @author Darya and Alesya
 */
public class Server {

    static int port;
    public boolean isRunning;
    public ServerSocket serverSocket;
    public DataBase db;    
    public Logger log = Logger.getLogger(Server.class);

    public Server(int port) {
        this.port = port;
        PropertyConfigurator.configure ("src/main/java/com/mycompany/aipos_pop3_server/log4j.properties");
        log.info("Hello this is an info message");
        db = new DataBase();
        List<String> list = db.getInfo();
        System.out.println("LIST: " + list);
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
            log.info("Start server");
            serverSocket = new ServerSocket(port);

            int numOfClients = 1;
            while (true) {
                log.info("New client");
                log.debug("New client");
                Socket client = serverSocket.accept();
                System.out.println("number of clients " + numOfClients);

                Runnable r = new ServerHandler(client);
                Thread thread = new Thread(r);
                thread.start();

                numOfClients++;
            }
        } catch (IOException exeption) {
            log.debug("Start server error!");
            log.info("Start server error!");
        }
    }

    public static void main(String[] args) {
       // int port = Integer.parseInt(args[1]);
       // System.out.println(port);
        int port = 110;
        Server server = new Server(port);
        server.start();
    }
}
