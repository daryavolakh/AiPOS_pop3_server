
package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;


public class RETRCommand implements Command {
    public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RETRCommand.class);
    
    @Override
    public void execute() {
        try {
            String userName = ServerHandler.username;
            
            int attribute = Integer.parseInt(ServerHandler.info);
            String result = ServerHandler.db.getMessageRETR(ServerHandler.username, Integer.parseInt(ServerHandler.info));
            ServerHandler.out.println(result);
            log.info("S: " + ServerHandler.db.getMessageRETR(userName, attribute));
            
        } catch (MessagingException ex) {
            Logger.getLogger(RETRCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
