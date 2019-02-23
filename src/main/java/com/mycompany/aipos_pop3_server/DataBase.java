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
    
    public boolean auth(String pass) throws SQLException{
        Statement statement = connection.createStatement();

			ResultSet resultSet = statement
					.executeQuery("SELECT password FROM users WHERE password="
							+ pass + ";");
			
        if(resultSet.next()){
            
            return true;
        }
        else{
            return false;
        }
        
    }
    
    public List<String> getInfo() {
        List<String> list = new ArrayList<String>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        }
        
        catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
	}
        return list;
    }
}
