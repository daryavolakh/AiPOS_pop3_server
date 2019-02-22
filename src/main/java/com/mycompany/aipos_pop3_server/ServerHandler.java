package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.log4j.Logger;

/*This class process data recieved from client  through one socket connection*/
class ServerHandler implements Runnable {

    private Socket incoming;
    
    public org.apache.log4j.Logger log = Logger.getLogger(ServerHandler.class);
    
    public ServerHandler(Socket incoming) {
        this.incoming = incoming;
    }

    public void run() {        
        log.info("New client");
        try (InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inStream, "UTF-8");

            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"), true);
            /* true - auto cleanning*/
            out.println("HELLO, ALESYA! Enter quit to exit! ");
            log.info("Send message to client");
            //send back client's data
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();                
                log.info("Recieve data '" + line + "' from client");
               /* if (line.trim().equals("quit") || line.trim().equals("QUIT")) {
                    done = true;
                    break;
                }*/
            }
        } catch (IOException exception) {
            System.out.println("Can't get input data");
            log.info("Can't get inout data");
        }
    }
}
