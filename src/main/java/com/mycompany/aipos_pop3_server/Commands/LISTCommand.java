package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;
/*
 * @author Alesya
 */
public class LISTCommand implements Command {

    public Logger log = Logger.getLogger(LISTCommand.class);

    @Override
    public void execute() {
        if (ServerHandler.db.getNumberOfMessages(ServerHandler.username) > 0) {
            ServerHandler.out.println("+OK" + ServerHandler.db.getNumberOfMessages(ServerHandler.username) + "  messages (" + ServerHandler.db.getAllCharacters(ServerHandler.username) + " octets)\r\n");
            ServerHandler.out.println(ServerHandler.db.getMessageInfo(ServerHandler.username));
            log.info("+OK " + ServerHandler.db.getNumberOfMessages(ServerHandler.username) + "  messages (" + ServerHandler.db.getAllCharacters(ServerHandler.username) + " octets)" + ServerHandler.db.getMessageInfo(ServerHandler.username));
        } else {
            ServerHandler.out.println("-ERR no such message");
            log.error("-ERR no such message");
        }
    }
}
