package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.log4j.Logger;

/*This class process data recieved from client  through one socket connection*/
class ServerHandler implements Runnable {

    private Socket incoming;
    public Server server;
    public DataBase db;
    public Logger log = Logger.getLogger(ServerHandler.class);
    
    public ServerHandler(Socket incoming, Server incomingserver) {
        this.incoming = incoming;
        server = incomingserver;
        db = new DataBase();
    }

    public void run() {        
        log.info("New client");
        String username = "";
        String info = "";
        String command = "";
        try (InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inStream, "UTF-8");
            
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"), true);
            /* true - auto cleanning*/
            
            String message = "+OK POP3 server ready <>";
            out.println(message);
            log.info("S: '" + message);
            //send back client's data
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine(); 
                if (line.contains(" ")){ 
                    int index = line.indexOf(" "); 
                    info = line.substring(index + 1, line.length()); 
                    command = line.substring(0, index); 
                } else { 
                    command = line; 
                }
                log.info("C: '" + line);
               if (line.trim().equals("QUIT")) {
                    db.deleteAllFromDeletebox(username);
                    message = "+OK dewey POP3 server signing off";
                    out.println(message);
                    log.info("S: '" + message);
                    done = true;
                    break;
                } 
               
               if (command.trim().equals("USER")) {
                    username = info;

                    if (db.checkUser(info)) {
                        message = "+OK User accepted";
                        out.println(message);
                        log.info("S: '" + message);
                    } else {
                        message = "-ERR never heard of mailbox name";
                        out.println(message);
                        log.info("S: '" + message);
                    }
                } 
                
                else if (command.trim().equals("PASS")) {
                    if (db.checkPass(username, info)) {
                        message = "+OK Pass accepted";
                        out.println(message);
                        log.info("S: '" + message);
                    } else {
                        message = "-ERR invalid password";
                        out.println(message);
                        log.info("S: '" + message);
                    }
                } 

                else if (command.trim().equals("RSET")) {
                    db.insertFromDeletebox(username, info);
                    message = "+OK";
                    out.println(message);
                    log.info("S: '" + message);
                } 
                
                else if (command.trim().equals("DELE")) {
                    if (db.delete(username, info)) {
                        message = "+OK message deleted";
                        out.println(message);
                        log.info("S: '" + message);
                    } else {
                        message = "-ERR no such message";
                        out.println(message);
                        log.info("S: '" + message);
                    }
                } 
                
                else if (command.trim().equals("TOP")) {
                    // +OK top of message follows
                    //-ERR no such message
                    message = "+OK top of message follows";
                    out.println(message);
                    log.info("S: '" + message);
                } 
                
                else if (command.trim().equals("APOP")) {
                    //+OK maildrop locked and ready
                    //-ERR permission denied
                    message = "+OK maildrop locked and ready";
                    out.println(message);
                    log.info("S: '" + message);
                }
               
               else if(line.trim().equals("STAT")){
                   message = "+OK "+ db.getNumberOfMessages(username)+" "+ db.getAllCharacters(username);
                   out.println(message);
                   log.info("S: '" + message);
               }
               
               else if(line.trim().equals("LIST")){
                   message = "+OK"+ db.getNumberOfMessages(username)+"  messages ("+db.getAllCharacters(username)+" octets)";
                   out.println(message);
                   log.info("S: '" + message);
                   message = db.getMessageInfo(username);
                   out.println(db.getMessageInfo(username));
                   log.info("S: '" + message);
               }
               
               else if(line.trim().equals("UIDL")){
                   message = "+OK";
                   out.println(message);
                   log.info("S: '" + message);
                   message = db.getMessages(username);
                   out.println(message);
                   log.info("S: '" + message);
               }
               
               else if(command.equals("RETR")){
                   message = "+OK";
                   out.println(message);
                   log.info("S: '" + message);
                   message = db.getMessage(username, Integer.parseInt(info));
                   out.println(message);
                   log.info("S: '" + message);
               }
               
               else if (line.trim().equals("NOOP")) { 
                    message = "+OK";
                   out.println(message);
                   log.info("S: '" + message);
                }

                else {
                    message = "-ERR invalid command" + line;
                    out.println(message);
                    log.info("S: '" + message);
                }
            }
        } catch (IOException exception) {
            System.out.println("Can't get input data");
            log.info("Can't get inout data");
        } 
    }
}