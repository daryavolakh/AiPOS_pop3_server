package com.mycompany.aipos_pop3_server.Commands;

import com.mycompany.aipos_pop3_server.ServerHandler;
import java.util.HashMap;
import java.util.Map;
/*
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
  
   public void findAndRun(String dataFromClient){
       try{ 
           run(commands.get(dataFromClient));
        } catch(NullPointerException e ){ 
            message = "-ERR invalid command " + dataFromClient;
                    ServerHandler.out.println(message);
                    System.out.println(message);
                    log.error("S: '" + message);
        } 
        commands.get(dataFromClient);
   }
   
   public void run(Command command){
       command.execute();       
   }
}