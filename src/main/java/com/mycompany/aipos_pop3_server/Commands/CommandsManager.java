package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.DataBase;
import com.mycompany.aipos_pop3_server.ServerHandler;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Алеся
 */
public class CommandsManager {
   
  public Map<String, Command> commands = new HashMap<String, Command>();
  String message;
  public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CommandsManager.class);
  
  public CommandsManager(){
      commands.put("DELE", new DELECommand());
      commands.put("LIST", new LISTCommand());
      commands.put("PASS", new PASSCommand());
      commands.put("RETR", new RETRCommand());
      commands.put("RSET", new RSETCommand());
      commands.put("STAT", new STATCommand());
      commands.put("UIDL", new UIDLCommand());
      commands.put("USER", new USERCommand());
      commands.put("QUIT", new QUITCommand());
      
  }
  
   public String findAndRun(String command, String info, String username){
       
         return  commands.getOrDefault(command, new DefaultCommand()).execute(info,username);
        
   }
   
   
}
