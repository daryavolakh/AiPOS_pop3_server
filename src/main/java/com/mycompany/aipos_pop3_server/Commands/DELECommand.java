package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;
import com.mycompany.aipos_pop3_server.DataBase;
/*
 * @author Alesya and Dasha
 */
public class DELECommand implements Command {

    public Logger log = Logger.getLogger(DELECommand.class);

    @Override
    public String execute(String info, String username) {
        DataBase db = new DataBase();
        String message;
        if (db.delete(username, Integer.parseInt(info))) {
            message = "+OK message deleted";
          //  ServerHandler.out.println(message);
            log.info("S: " + message);
        } else {
            message = "-ERR no such message";
           // ServerHandler.out.println(message);
            log.error("S: " + message);
        }
        
        return message;
    }
}
