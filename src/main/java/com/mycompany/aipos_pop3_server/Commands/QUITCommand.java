package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.DataBase;
import org.apache.log4j.Logger;

/**
 *
 * @author Alesya
 */
public class QUITCommand implements Command {

    public Logger log = Logger.getLogger(QUITCommand.class);

    @Override
    public String execute(String info, String username) {
        DataBase db = new DataBase();
        db.deleteAllFromDeletebox(username);
        String message = "+OK dewey POP3 server signing off";
      //  ServerHandler.out.println(message);
        log.info("S: " + message);
        
        return message;
    }
}
