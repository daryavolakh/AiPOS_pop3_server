/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import java.lang.*;

/**
 *
 * @author Алеся
 */
public class DELECommand implements Command {
    public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DELECommand.class);
    @Override
    public void execute() {
        String message;
        if (ServerHandler.db.delete(ServerHandler.username, Integer.parseInt(ServerHandler.info))) {
                        message = "+OK message deleted";
                        ServerHandler.out.println(message);
                        log.info("S: '" + message);
                    } else {
                        message = "-ERR no such message";
                        ServerHandler.out.println(message);
                        log.error("S: '" + message);
                    }
    }
    
}
