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
            Scanner in = new Scanner(inStream, "windows-1251");

            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "windows-1251"), true);
            /* true - auto cleanning*/

            String message = "+OK POP3 server ready <>";
            out.println(message);
            log.info("S: '" + message);
            //send back client's data
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains(" ")) {
                    int index = line.indexOf(" ");
                    info = line.substring(index + 1, line.length());
                    command = line.substring(0, index);
                } else {
                    command = line;
                }
                log.info("C: '" + line);
                if (command.trim().equals("QUIT")) {
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
                        log.error("S: '" + message);
                    }
                } else if (command.trim().equals("PASS")) {
                    if (db.checkPass(username, info)) {
                        message = "+OK Pass accepted";
                        out.println(message);
                        log.info("S: '" + message);
                    } else {
                        message = "-ERR invalid password";
                        out.println(message);
                        log.error("S: '" + message);
                    }
                } else if (command.trim().equals("RSET")) {
                    db.insertFromDeletebox(username, info);
                    message = "+OK";
                    out.println(message);
                    log.info("S: '" + message);
                } else if (command.trim().equals("DELE")) {
                    if (db.delete(username, Integer.parseInt(info))) {
                        message = "+OK message deleted";
                        out.println(message);
                        log.info("S: '" + message);
                    } else {
                        message = "-ERR no such message";
                        out.println(message);
                        log.error("S: '" + message);
                    }
                } else if (command.trim().equals("TOP")) {
                    // +OK top of message follows
                    //-ERR no such message
                    message = "+OK top of message follows";
                    out.println(message);
                    log.info("S: '" + message);
                } else if (command.trim().equals("APOP")) {
                    //+OK maildrop locked and ready
                    //-ERR permission denied
                    message = "+OK maildrop locked and ready";
                    out.println(message);
                    log.info("S: '" + message);
                } else if (line.trim().equals("STAT")) {
                    if (db.getNumberOfMessages(username) > 0) {
                        out.println("+OK " + db.getNumberOfMessages(username) + " " + db.getAllCharacters(username));
                        log.info("S: +OK " + db.getNumberOfMessages(username) + " " + db.getAllCharacters(username));
                    } else {
                        out.println("-ERR no such message");
                        log.error("-ERR no such message");
                    }
                } else if (line.trim().equals("LIST")) {
                    if (db.getNumberOfMessages(username) > 0) {
                        out.println("+OK" + db.getNumberOfMessages(username) + "  messages (" + db.getAllCharacters(username) + " octets)\r\n");
                        out.println(db.getMessageInfo(username));
                        log.info("+OK " + db.getNumberOfMessages(username) + "  messages (" + db.getAllCharacters(username) + " octets)" + db.getMessageInfo(username));
                    } else {
                        out.println("-ERR no such message");
                        log.error("-ERR no such message");
                    }
                } else if (line.trim().equals("UIDL")) {
                    out.println(db.getMessages(username));
                    log.info("S: " + db.getMessages(username));
                } else if (command.equals("RETR")) {
                    out.println(db.getMessageRETR(username, Integer.parseInt(info)));
                    log.info("S: " + db.getMessageRETR(username, Integer.parseInt(info)));
                } else if (line.trim().equals("NOOP")) {
                    out.println("+OK");
                    log.info("S: +OK");
                } else {
                    message = "-ERR invalid command " + line;
                    out.println(message);
                    log.error("S: '" + message);
                }
            }
        } catch (IOException exception) {
            System.out.println("Can't get input data");
            log.error("Can't get input data");
        }
    }
}
