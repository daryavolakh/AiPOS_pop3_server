/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;

/**
 *
 * @author Алеся
 */
public class PASSCommand implements Command {
    public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PASSCommand.class);
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
