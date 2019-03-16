/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.DataBase;
import com.mycompany.aipos_pop3_server.ServerHandler;

/**
 *
 * @author Алеся
 */
public class USERCommand implements Command {

    public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(USERCommand.class);
    public String message;
    
    
    @Override
    public void execute() {
    ServerHandler.username = ServerHandler.info;

                    if (ServerHandler.db.checkUser(ServerHandler.info)) {
                        message = "+OK User accepted";
                        ServerHandler.out.println(message);
                        log.info("S: '" + message);
                    } else {
                        message = "-ERR never heard of mailbox name";
                        ServerHandler.out.println(message);
                        log.error("S: '" + message);
                    }
    }
    
}
