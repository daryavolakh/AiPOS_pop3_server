package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;

/**
 *
 * @author �����
 */
public class DefaultCommand implements Command {

    public Logger log = Logger.getLogger(DefaultCommand.class);
    String message;

    @Override
    public String execute(String info, String username) {
        message = "-ERR invalid command ";// + ServerHandler.command;
        //ServerHandler.out.println(message);
       // System.out.println(message);
        log.error("S: '" + message);
        
        return message;
    }
}
