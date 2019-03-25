package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.DataBase;
import org.apache.log4j.Logger;
/*
 * @author Alesya and Dasha
 */
public class PASSCommand implements Command {

    public Logger log = Logger.getLogger(PASSCommand.class);

    @Override
    public String execute(String info, String username) {
        String message;
        DataBase db = new DataBase();

        if (db.checkPass(username, info)) {
            message = "+OK Pass accepted";
         //   ServerHandler.out.println(message);
            log.info("S: " + message);
        } else {
            message = "-ERR invalid password";
         //   ServerHandler.out.println(message);
            log.error("S: " + message);
        }
        
        return message;
    }
}
