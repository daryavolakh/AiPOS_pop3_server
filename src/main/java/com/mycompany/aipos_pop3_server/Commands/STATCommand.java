/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.DataBase;
import org.apache.log4j.Logger;

/**
 *
 * @author Alesya
 */
public class STATCommand implements Command {

    public org.apache.log4j.Logger log = Logger.getLogger(STATCommand.class);

    @Override
    public String execute(String info, String username) {
        String message;
        DataBase db = new DataBase();
        if (db.getNumberOfMessages(username) > 0) {
            message = "+OK " + db.getNumberOfMessages(username) + " " + db.getAllCharacters(username);
           // ServerHandler.out.println("+OK " + ServerHandler.db.getNumberOfMessages(ServerHandler.username) + " " + ServerHandler.db.getAllCharacters(ServerHandler.username));
            log.info("S: " + message);
        } else {
            message = "-ERR no such message";
           // ServerHandler.out.println("-ERR no such message");
            log.error("S: " + message);
        }
        
        return message;
    }

}
