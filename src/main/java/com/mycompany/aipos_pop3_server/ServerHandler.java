/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author Asus
 */
/*Этот класс обрабатывает данные, получаемые сервером от клиента через одно сокетное соединение*/
class ServerHandler implements Runnable {

    private Socket incoming;
    public Server server;

    public ServerHandler(Socket incomingSocket, Server incomingserver) {
        incoming = incomingSocket;
        server = incomingserver;
    }

    public void run() {
        try (InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inStream, "UTF-8");

            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"), true);
            /* true - auto cleanning*/
            out.println("+OK POP3 server ready <1896.697170952@dbc.mtview.ca.us>");
            //передать обратно данные, которые получили от клиента
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                if (line.trim().equals("quit") || line.trim().equals("QUIT")) {
                    done = true;
                    //this.quit();
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
                    String password = in.nextLine();
                    
                    try {
                        if (server.db.auth(password)){
                            System.out.println("Password is true");
                        }
                        
                    } catch (SQLException ex) {
                        System.out.println("Password is wrong");
                    }
                }
                
                
            }
        } catch (IOException exception) {
            System.out.println("Something went wrong");
        }
    }
}
