package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;

/**
 *
 * @author Alesya
 */
public class QUITCommand implements Command {

    public Logger log = Logger.getLogger(QUITCommand.class);

    @Override
    public void execute() {
        ServerHandler.db.deleteAllFromDeletebox(ServerHandler.username);
        String message = "+OK dewey POP3 server signing off";
        ServerHandler.out.println(message);
        log.info("S: '" + message);
    }
}
