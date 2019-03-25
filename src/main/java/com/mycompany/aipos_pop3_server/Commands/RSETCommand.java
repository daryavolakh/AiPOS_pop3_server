package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.DataBase;
import org.apache.log4j.Logger;

/*
 * @author Alesya and Dasha
 */
public class RSETCommand implements Command {

    public Logger log = Logger.getLogger(RSETCommand.class);

    @Override
    public String execute(String info, String username) {
        DataBase db = new DataBase();
        db.insertFromDeletebox(username);
        String message = "+OK";
       // ServerHandler.out.println(message);
        log.info("S: " + message);
        
        return message;
    }

}
