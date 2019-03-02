package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.Base64.Decoder;
import java.util.logging.Level;

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
        String username = "alesya213";
        String info = "";
        String command = "";
        try (InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inStream, "UTF-8");
            
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"), true);
            /* true - auto cleanning*/
            out.println("+OK POP3 server ready <>");
            log.info("Send message to client");
            //send back client's data
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine(); 
                if (line.contains(" ")){ 
                    int index = line.indexOf(" "); 
                    info = line.substring(index + 1, line.length()); 
                    command = line.substring(0, index); 
                    } 

                    else { 
                    command = line; 
                    }
                log.info("Recieve data '" + line + "' from client");
               if (line.trim().equals("QUIT")) {
                    out.println("+OK dewey POP3 server signing off");
                    done = true;
                    break;
                }
               
               else if (line.trim().equals("CAPA")) {
                    out.println("-ERR invalid command CAPA");
                }
                
               else if(line.trim().equals("AUTH PLAIN")){
                    out.println("-ERR invalid command AUTH PLAIN");
                } 
               
               else if(line.trim().equals("USER alesya213")){
                   out.println("+OK User accepted");
               }
               
               else if(line.trim().equals("PASS 123")){
                   out.println("+OK Pass accepted");
               }
               
               else if(line.trim().equals("STAT")){
                   out.println("+OK "+ server.db.getNumberOfMessages(username)+" "+ server.db.getAllCharacters(username));
               }
               
               else if(line.trim().equals("LIST")){
                   out.println("+OK"+ server.db.getNumberOfMessages(username)+"  messages ("+server.db.getAllCharacters(username)+" octets)");
                   out.println(server.db.getMessageInfo(username));
               }
               
               else if(line.trim().equals("UIDL")){
                   out.println("+OK");
                   out.println(server.db.getMessages(username));
               }
               
               else if(command.equals("RETR")){
                   out.println("+OK");
                   out.println(server.db.getMessage(username, Integer.parseInt(info)));
               }
               
               if (line.trim().equals("NOOP")) { 
                out.println("+OK");
                }
            }
        } catch (IOException exception) {
            System.out.println("Can't get input data");
            log.info("Can't get inout data");
        } 
    }
}