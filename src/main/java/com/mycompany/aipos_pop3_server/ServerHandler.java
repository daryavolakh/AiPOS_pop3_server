package com.mycompany.aipos_pop3_server;

import com.mycompany.aipos_pop3_server.Commands.CommandsManager;
import com.mycompany.aipos_pop3_server.Commands.STATCommand;
import com.mycompany.aipos_pop3_server.Commands.UIDLCommand;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.Base64.Decoder;
import java.util.logging.Level;

public class ServerHandler implements Runnable {

    private Socket incoming;
    public Server server;
    public static DataBase db;
    public Logger log = Logger.getLogger(ServerHandler.class);
    public static String username = "";
    public static PrintWriter out;
    public static String info = "";
    public static String command;

    public ServerHandler(Socket incoming, Server incomingserver) {
        this.incoming = incoming;
        server = incomingserver;
        db = new DataBase();
    }

    public void run() {
        log.info("New client");

        CommandsManager manager = new CommandsManager();
        try (InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inStream, "windows-1251");

            out = new PrintWriter(
                    new OutputStreamWriter(outStream, "windows-1251"), true);
            String message = "+OK POP3 server ready <>";
            System.out.println("S: " + message);
            out.println(message);
            log.info("S: " + message);
            //send back client's data
            boolean done = false;

            while (!done && in.hasNextLine()) {
                String line = in.nextLine();                
                System.out.println("C: '" + line);
                if (line.contains(" ")) {
                    int index = line.indexOf(" ");
                    info = line.substring(index + 1, line.length());
                    command = line.substring(0, index);
                } else {
                    command = line;
                }
                log.info("C: " + line);
                String answer = manager.findAndRun(command, info, username);
                out.println(answer);
                
                System.out.println("S: " + answer);

            }
        } catch (IOException exception) {
            System.out.println("Can't get input data");
            log.error("Can't get inout data");
        }
    }
}
