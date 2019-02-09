package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author Darya and Alesya
 */
public class Server {
    static int port; 
    public boolean isRunning;
    public ServerSocket serverSocket;
    public DataBase db = new DataBase();
    public static Logger logger = Logger.getLogger(Server.class
            .getName());; 
        
    public static FileHandler fh;  
    public SimpleDateFormat time;
    
  public Server(int port) {
        this.port = port;
        this.time = new SimpleDateFormat("HH:mm:ss");
    }
  
  public void quit(){
      System.out.println("QUIT");
      this.isRunning = false;
      try {
        this.serverSocket.close();
      }
      
      catch(Exception exception) {
          System.out.println("Something went wrong");
        }
  }
  
  public void start(){
      this.isRunning = true;
      
      
      try
    {   
        fh = new FileHandler("D:/LogFile.log");
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
        serverSocket = new ServerSocket(port);
        logger.fine(time.format(new Date()) + " Listen"); 
        int numOfClients = 1;
        while(true){ 
            Socket client = serverSocket.accept();
            logger.fine(time.format(new Date()) + " Accept client"); 
            System.out.println("Spawning " + numOfClients);
            Runnable r = new ServerHandler(client);
            Thread thread = new Thread(r);
            logger.fine(time.format(new Date()) + " Create thread for client"); 
            thread.start();
            
            numOfClients++;
           }
        }
    catch (IOException exeption) {
        logger.severe(time.format(new Date()) + " Error");
        System.out.println("Something went wrong 1");
        }
  }
  
  public static void main(String[] args) {
    int port = 110;
    
    Server server = new Server(port);
    
    
    server.start();
  }
  
}