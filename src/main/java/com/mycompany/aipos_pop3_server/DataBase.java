package com.mycompany.aipos_pop3_server;

import java.sql.*;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

//отправка сообщений с аттачментами (архимы, картинки) в разных кодировках
public class DataBase {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/aipos?useSSL=false";
    public Logger log = Logger.getLogger(DataBase.class);

    private Connection connection;
    private Driver driver;

    private static final String DELETE_FROM_DELETEBOX = "DELETE FROM deletebox WHERE username=? AND message=?;";
    public DataBase() {
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

    public int getNumberOfMessages(String username) {
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
            log.info(e.getMessage());
        }
        return numOfMessage;
    }

    public int getAllCharacters(String username) {
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
            log.info(e.getMessage());
        }
        return numOfChar;
    }

    public String getMessageInfo(String username) {
        String message = "";
        int numOfMes = 0;
        int numOfChar = 0;
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + username + "';");
            while (resultSet.next()) {
                numOfMes++;
                message += numOfMes + " " + resultSet.getString("message").length() + "\r\n";
            }

        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            log.info(e.getMessage());
        }
        message += ".";
        return message;
    }

    public String getMessages(String username) {
        String message = "+OK\r\n";
        int numOfMes = 0;
        int numOfChar = 0;
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + username + "';");
            while (resultSet.next()) {
                numOfMes++;
                message += numOfMes + " " + resultSet.getString("message") + "\r\n";
            }
            message += ".";
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            message = "-ERR no such message";
            log.error(e.getMessage());
        }

        return message;
    }

    public String getMessage(String username, int mesInd) {
        String message = "+OK\r\n";
        mesInd -= 1;
        int numOfMes = 0;
        int numOfChar = 0;
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + username + "';");
            while (resultSet.next()) {
                if (mesInd == numOfMes) {
                    message += (numOfMes + 1) + " " + resultSet.getString("message") + "\r\n";
                }

                numOfMes++;
            }
            message += ".";
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            message = "-ERR no such message";
            log.error(e.getMessage());
        }
        
        return message;
    }

    public boolean checkUser(String user) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(("SELECT username FROM users WHERE username='" + user + "';"));
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return false;
    }

    public boolean checkPass(String user, String pass) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT password FROM users WHERE username='" + user + "';");
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
            log.error(e.getMessage());
        }

        return false;
    }

    public boolean findInTable(String user, String message, String table) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT message FROM " + table + " WHERE username='" + user + "' AND message = '" + message + "';");
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return false;
    }

    public boolean delete(String user, int number) {
        List<String> list = new ArrayList<String>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + user + "'");

            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            
            String message = list.get(number - 1);

            if (findInTable(user, message, "mailbox")) {
                PreparedStatement stUpdate = connection.prepareStatement("UPDATE mailbox SET deleted=true WHERE username='" + user + "' AND message='" + message + "' AND deleted=false;");
                            
                stUpdate.execute();

                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return false;
    }

    public void insertFromDeletebox(String user, String message) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE mailbox SET deleted=false WHERE username='" + user + "' AND message='" + message + "' AND deleted=true;");
            
            ResultSet resultSet = statement.executeQuery();      

            while (resultSet.next()) {
                statement.execute();
            }
           
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public void deleteAllFromDeletebox(String user) {
        try {
            PreparedStatement stDelete = connection.prepareStatement("DELETE FROM mailbox WHERE username='" + user + "'AND deleted=true;");

            stDelete.execute();
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
