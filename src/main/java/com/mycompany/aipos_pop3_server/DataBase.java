package com.mycompany.aipos_pop3_server;

import java.sql.*;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataBase {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "summernothot";
    private static final String URL = "jdbc:mysql://localhost:3306/aipos?useSSL=false";    

    // class path - переменная соержащая ссылки на пути, где находятся важные файлы для вашего проекта
    // батник в котором при запуске указывается class.path
    // или
    // бросить туда, где лежат библиотеки java
    private Connection connection;
    private Driver driver;
    
    public DataBase(){ //throws ClassNotFoundException {
        try {
            driver = new FabricMySQLDriver();
	} catch (SQLException ex) {
            System.out.println("Creating driver error!");
            return;
	}

	try {
            DriverManager.registerDriver(driver);
	} catch (SQLException ex) {
            System.out.println("Can't register driver!");
            return;
	}

	try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           // connection = DriverManager.getConnection(URL, properties);
            System.out.println("Get connection!");
        } catch (SQLException ex) {
            System.out.println("Can't create connection!");
            return;
	}
    }
    
    public List<String> getInfo() {
        List<String> list = new ArrayList<String>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM temp;");
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