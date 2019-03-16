package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;

/*
 * @author Alesya and Dasha
 */
public class RETRCommand implements Command {

    public Logger log = Logger.getLogger(RETRCommand.class);

    @Override
    public void execute() {
        String userName = ServerHandler.username;
        int attribute = Integer.parseInt(ServerHandler.info);
        ServerHandler.out.println(ServerHandler.db.getMessageRETR(ServerHandler.username, Integer.parseInt(ServerHandler.info)));
        log.info("S: " + ServerHandler.db.getMessageRETR(userName, attribute));
    }

}
