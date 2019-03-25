package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.DataBase;
import org.apache.log4j.Logger;

/**
 *
 * @author Alesya
 */
public class UIDLCommand implements Command {

    public Logger log = Logger.getLogger(UIDLCommand.class);

    @Override
    public String execute(String info, String username) {
        DataBase db = new DataBase();
        String message = db.getMessages(username);
        //ServerHandler.out.println(message);
        log.info("S: " + message);
        
        return message;
    }
}
