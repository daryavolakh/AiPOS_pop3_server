package com.mycompany.aipos_pop3_server;

import java.sql.*;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.*;
import org.apache.log4j.Logger;

public class DataBase {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/aipos?useSSL=false";   
    public Logger log = Logger.getLogger(DataBase.class);

    private Connection connection;
    private Driver driver;

    private static final String CHECK_USER = "SELECT user FROM users WHERE user=?;";
    private static final String CHECK_PASSWORD = "SELECT password FROM users WHERE user=?;";
    private static final String INSERT_DELETED = "INSERT INTO deletebox VALUES(?,?);";
    private static final String DELETE_FROM_MAILBOX = "DELETE FROM mailbox WHERE username=? AND message=?;";
    private static final String INSERT_MAILBOX = "INSERT INTO mailbox VALUES(?,?);";
    private static final String DELETE_FROM_DELETEBOX = "DELETE FROM deletebox WHERE username=? AND message=?;";
    
    public DataBase(){ 
        try {
            driver = new FabricMySQLDriver();
	} catch (SQLException ex) {
            System.out.println("Creating driver error!");
            log.info("Creating driver error!");
            return;
	}

	try {
            DriverManager.registerDriver(driver);
	} catch (SQLException ex) {
            System.out.println("Can't register driver!");
            log.info("Can't register driver!");
            return;
	}

	try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Get connection!");
            log.info("Get connection with DB");
        } catch (SQLException ex) {
            System.out.println("Can't create connection!");
            log.info("Can't create connection with DB");
            return;
	    }
    }
    
    public int getNumberOfMessages(String username){
        int numOfMessage = 0;
         try { 
        Statement statement = connection.createStatement(); 
        
        ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + username + "';"); 
        while (resultSet.next()) { 
        numOfMessage++;
        } 
        } catch (SQLException e) { 
        e.getMessage(); 
        e.printStackTrace(); 
        } 
        return numOfMessage; 
    }
    
    public int getAllCharacters(String username){
        int numOfChar = 0;
         try { 
        Statement statement = connection.createStatement(); 
        
        ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + username + "';"); 
        int i = 0;
        while (resultSet.next()) { 
            numOfChar += resultSet.getString("message").length();
            i++;
        } 
        } catch (SQLException e) { 
        e.getMessage(); 
        e.printStackTrace(); 
        } 
        return numOfChar; 
    }

     public String getMessageInfo(String username){
        String message="";
        int numOfMes = 0;
        int numOfChar = 0;
         try { 
        Statement statement = connection.createStatement(); 
        
        ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + username + "';"); 
        while (resultSet.next()) { 
            numOfMes++;
            message += numOfMes+" "+resultSet.getString("message").length()+"\r\n";
        } 
        
        } catch (SQLException e) { 
        e.getMessage(); 
        e.printStackTrace(); 
        } 
         message += ".";
        return message;
    }
     
     public String getMessages(String username){
        String message="";
        int numOfMes = 0;
        int numOfChar = 0;
         try { 
        Statement statement = connection.createStatement(); 
        
        ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + username + "';"); 
        while (resultSet.next()) { 
            numOfMes++;
            message += numOfMes+" "+resultSet.getString("message")+"\r\n";
        } 
        
        } catch (SQLException e) { 
        e.getMessage(); 
        e.printStackTrace(); 
        } 
         message += ".";
        return message;
    }
     
     public String getMessage(String username, int mesInd){
        String message="";
        mesInd -=1;
        int numOfMes = 0;
        int numOfChar = 0;
         try { 
        Statement statement = connection.createStatement(); 
        
        ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + username + "';"); 
        while (resultSet.next()) { 
            if(mesInd == numOfMes){
                message += (numOfMes+1)+" "+resultSet.getString("message")+"\r\n";
            }
            
            numOfMes++;
        } 
        
        } catch (SQLException e) { 
        e.getMessage(); 
        e.printStackTrace(); 
        message += "This message not found";
        } 
         message += ".";
        return message;
    }

     public boolean checkUser(String user) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(("SELECT user FROM users WHERE user='" + user + "';"));
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPass(String user, String pass) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT password FROM users WHERE user='" + user + "';");
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(pass)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }

        return false;
    }

    public boolean findInTable(String user, String message, String table) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT message FROM " + table + " WHERE user='" + user + "' AND message = '" + message + "';");
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String user, String message) {
        try {
            if (findInTable(user, message, "mailbox")) {
                PreparedStatement stInsert = connection.prepareStatement(INSERT_DELETED);
                stInsert.setString(1, user);
                stInsert.setString(2, message);

                stInsert.execute();

                PreparedStatement stDelete = connection.prepareStatement(DELETE_FROM_MAILBOX);
                stDelete.setString(1, user);
                stDelete.setString(2, message);

                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }

        return false;
    }
    
    public void insertFromDeletebox(String user, String message){
        try {
            if (findInTable(user, message, "deletebox")) {
                PreparedStatement stInsert = connection.prepareStatement(INSERT_MAILBOX);
                stInsert.setString(1, user);
                stInsert.setString(2, message);

                stInsert.execute();

                PreparedStatement stDelete = connection.prepareStatement(DELETE_FROM_DELETEBOX);
                stDelete.setString(1, user);
                stDelete.setString(2, message);

                stDelete.execute();
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
    
    public void deleteAllFromDeletebox(String user){
        try {
            PreparedStatement stDelete = connection.prepareStatement("DELETE FROM deletebox WHERE username='" + user + "';");
            stDelete.setString(1, user);
            
            stDelete.execute();
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
