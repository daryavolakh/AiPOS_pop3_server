package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;

/**
 *
 * @author Alesya
 */
public class UIDLCommand implements Command {

    public Logger log = Logger.getLogger(UIDLCommand.class);

    @Override
    public void execute() {
        ServerHandler.out.println(ServerHandler.db.getMessages(ServerHandler.username));
        log.info("S: " + ServerHandler.db.getMessages(ServerHandler.username));
    }
}
