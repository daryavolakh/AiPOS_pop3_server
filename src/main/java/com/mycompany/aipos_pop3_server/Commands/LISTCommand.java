package com.mycompany.aipos_pop3_server.Commands;

//import com.mycompany.aipos_pop3_server.ServerHandler;
import org.apache.log4j.Logger;
import com.mycompany.aipos_pop3_server.DataBase;
/*
 * @author Alesya
 */
public class LISTCommand implements Command {

    public Logger log = Logger.getLogger(LISTCommand.class);

    @Override
    public String execute(String info, String username) {
        String message;
        DataBase db = new DataBase();
        if (db.getNumberOfMessages(username) > 0) {
            message = "+OK " + db.getNumberOfMessages(username) + " messages (" + db.getAllCharacters(username) + " octets)\r\n" + db.getMessageInfo(username);
            //ServerHandler.out.println("+OK" + ServerHandler.db.getNumberOfMessages(ServerHandler.username) + "  messages (" + ServerHandler.db.getAllCharacters(ServerHandler.username) + " octets)\r\n");
           // ServerHandler.out.println(ServerHandler.db.getMessageInfo(ServerHandler.username));
            log.info("S: " + message);
        } else {
            message = "-ERR no such message";
           // ServerHandler.out.println(message);
            log.error("S: " + message);
        }
        return message;
    }
}
