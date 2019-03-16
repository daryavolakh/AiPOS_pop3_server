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
public class RETRCommand implements Command {
    public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RETRCommand.class);
    
    @Override
    public void execute() {
        String userName = ServerHandler.username;
    int attribute = Integer.parseInt(ServerHandler.info);
        ServerHandler.out.println(ServerHandler.db.getMessageRETR(ServerHandler.username, Integer.parseInt(ServerHandler.info)));
        log.info("S: " + ServerHandler.db.getMessageRETR(userName, attribute));
    }
    
}
