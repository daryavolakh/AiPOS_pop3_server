/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;

/**
 *
 * @author Alesya
 */
public class STATCommand implements Command {

    public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(STATCommand.class);
    
    @Override
    public void execute() {
        if (ServerHandler.db.getNumberOfMessages(ServerHandler.username) > 0) {
                        ServerHandler.out.println("+OK " + ServerHandler.db.getNumberOfMessages(ServerHandler.username) + " " + ServerHandler.db.getAllCharacters(ServerHandler.username));
                        log.info("S: +OK " + ServerHandler.db.getNumberOfMessages(ServerHandler.username) + " " + ServerHandler.db.getAllCharacters(ServerHandler.username));
                    } else {
                        ServerHandler.out.println("-ERR no such message");
                        log.error("-ERR no such message");
                    }
    }
    
}
