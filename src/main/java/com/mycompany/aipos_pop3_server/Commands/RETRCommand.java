
package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.DataBase;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;


public class RETRCommand implements Command {
    public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RETRCommand.class);
    
    @Override
    public String execute(String info, String username) {
        String message="";
        DataBase db = new DataBase();
        try {
            String userName = username;
            
            int attribute = Integer.parseInt(info);
            message = db.getMessageRETR(username, attribute);
           // ServerHandler.out.println(message);
            log.info("S: " + message);
            
        } catch (MessagingException ex) {
            Logger.getLogger(RETRCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
    
}
