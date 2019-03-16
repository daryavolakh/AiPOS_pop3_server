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
public class RSETCommand implements Command {

    public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RSETCommand.class);
    
    @Override
    public void execute() {
        ServerHandler.db.insertFromDeletebox(ServerHandler.username, ServerHandler.info);
                    String message = "+OK";
                    ServerHandler.out.println(message);
                    log.info("S: '" + message);
    }
    
}
