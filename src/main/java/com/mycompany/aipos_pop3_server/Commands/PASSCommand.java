package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;
/*
 * @author Alesya and Dasha
 */
public class PASSCommand implements Command {

    public Logger log = Logger.getLogger(PASSCommand.class);

    @Override
    public void execute() {
        String message;

        if (ServerHandler.db.checkPass(ServerHandler.username, ServerHandler.info)) {
            message = "+OK Pass accepted";
            ServerHandler.out.println(message);
            log.info("S: '" + message);
        } else {
            message = "-ERR invalid password";
            ServerHandler.out.println(message);
            log.error("S: '" + message);
        }
    }
}
