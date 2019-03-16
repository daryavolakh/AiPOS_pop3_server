package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;

/*
 * @author Alesya and Dasha
 */
public class USERCommand implements Command {

    public Logger log = Logger.getLogger(USERCommand.class);
    public String message;

    @Override
    public void execute() {
        ServerHandler.username = ServerHandler.info;

        if (ServerHandler.db.checkUser(ServerHandler.info)) {
            message = "+OK User accepted";
            ServerHandler.out.println(message);
            log.info("S: '" + message);
        } else {
            message = "-ERR never heard of mailbox name";
            ServerHandler.out.println(message);
            log.error("S: '" + message);
        }
    }
}
