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
    
  public Server(int port) {
        this.port = port;
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
        logger.fine("fine message");
        
         
        this.serverSocket = new ServerSocket(this.port);
        int numOfClients = 1;
        while(true){ 
            Socket client = this.serverSocket.accept();
            
            System.out.println("Spawning " + numOfClients);
            Runnable r = new ServerHandler(client);
            Thread thread = new Thread(r);
            thread.start();
            logger.fine("fine message"); 
            numOfClients++;
           }
        }
    catch (IOException exeption) {
        logger.severe("error message");
        System.out.println("Something went wrong 1");
        }
  }
  
  public static void main(String[] args) {
    int port = 110;
    
    Server server = new Server(port);
    
    
    server.start();
  }
  /*Этот класс обрабатывает данные, получаемые сервером от клиента через одно сокетное соединение*/
  class ServerHandler implements Runnable{
      private Socket incoming;
      
      public ServerHandler(Socket incomingSocket){
          this.incoming = incomingSocket;
      }
      
      public void run(){
          try(InputStream inStream = incoming.getInputStream();
                  OutputStream outStream = incoming.getOutputStream()){
              Scanner in = new Scanner(inStream, "UTF-8");
              PrintWriter out = new PrintWriter(
              new OutputStreamWriter(outStream, "UTF-8"), true /* auto cleanning*/);
              out.println("HELLO, ALESYA! Enter quit to exit! ");
              //передать обратно данные, которые получили от клиента
                    boolean done = false;
                    while(!done && in.hasNextLine())
                    {
                        String line = in.nextLine();
                        if (line.trim().equals("quit") || line.trim().equals("QUIT")) {
                            done = true;
                            //this.quit();
                            break;
                        }
                        }
          }
          
          catch (IOException exception){
              System.out.println("Something went wrong");
          }
      }
  }
}