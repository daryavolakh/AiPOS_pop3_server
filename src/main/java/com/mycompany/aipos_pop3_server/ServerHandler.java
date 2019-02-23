package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.Base64.Decoder;

/*This class process data recieved from client  through one socket connection*/
class ServerHandler implements Runnable {

    private Socket incoming;
    public Server server;
    
    public Logger log = Logger.getLogger(ServerHandler.class);
    
    public ServerHandler(Socket incoming, Server incomingserver) {
        this.incoming = incoming;
        server = incomingserver;
    }

    public void run() {        
        log.info("New client");
        try (InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inStream, "UTF-8");

            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"), true);
            /* true - auto cleanning*/
            out.println("+OK POP3 server ready <1896.697170952@dbc.mtview.ca.us>");
            log.info("Send message to client");
            //send back client's data
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();                
                log.info("Recieve data '" + line + "' from client");
               if (line.trim().equals("quit") || line.trim().equals("QUIT")) {
                    done = true;
                    break;
                }
               
               if (line.trim().equals("CAPA")) {
                    out.println("+OK Capability list follows");
                    out.println("TOP");
                    out.println("QUIT");
                    out.println("RETR");
                    out.println("USER");
                    out.println(".");
                }
                
                if(line.trim().equals("AUTH PLAIN")){
                    out.println("+");
                    String incomingPassword = in.nextLine();
                    byte[] decodedBytes = Base64.getDecoder().decode(incomingPassword);
                    System.out.println(incomingPassword);
                    String decodedPass = new String(decodedBytes);
                    String target = ".com";
                    int index = decodedPass.indexOf(target);
                    int subIndex = index + target.length();
                    String password = decodedPass.substring(subIndex);
                    String username = decodedPass.substring(0, subIndex);
                    try {
                        if (server.db.auth(password)){
                            out.println("+OK Maildrop locked and ready");
                        }
                        
                    } catch (SQLException ex) {
                    } 
                } 
            }
        } catch (IOException exception) {
            System.out.println("Can't get input data");
            log.info("Can't get inout data");
        }
    }
}