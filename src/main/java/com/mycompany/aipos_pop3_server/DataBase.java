package com.mycompany.aipos_pop3_server;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.*;
import java.io.*;
import java.io.File;
import java.sql.*;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.util.*;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class DataBase {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/aipos?useSSL=false";
    public Logger log = Logger.getLogger(DataBase.class);

    private Connection connection;
    private Driver driver;

    public DataBase() {
        try {
            driver = new FabricMySQLDriver();
        } catch (SQLException ex) {
            // System.out.println("Creating driver error!");
            log.info("Creating driver error!");
            return;
        }

        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException ex) {
            // System.out.println("Can't register driver!");
            log.info("Can't register driver!");
            return;
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // System.out.println("Get connection!");
            log.info("Get connection with DB");
        } catch (SQLException ex) {
            // System.out.println("Can't create connection!");
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

    public String getMessageRETR(String username, int mesInd) throws MessagingException {
        String messageString = "+OK\r\n";
        int numOfMes = 0;
        Message message = new MimeMessage(Session.getInstance(System.getProperties()));
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT subject, message, mailFrom, username, filePath FROM mailbox WHERE username='" + username + "';");
            while (resultSet.next()) {
                if (numOfMes == mesInd - 1) {
                    //String tempMessage = message;

                    MimeMultipart multiPart = new MimeMultipart();

                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setText(resultSet.getString("message"));
                    message.setFrom(new InternetAddress(resultSet.getString("mailFrom")));
                    message.setSubject(resultSet.getString("subject"));
                    multiPart.addBodyPart(messageBodyPart);
                    if (resultSet.getString("filePath") != null && resultSet.getString("filePath") != "") {
                        MimeBodyPart attachment = new MimeBodyPart();
                        DataSource source = new FileDataSource(new File(resultSet.getString("filePath")));
                        attachment.setDataHandler(new DataHandler(source));
                        multiPart.addBodyPart(attachment);
                    }

                    message.setContent(multiPart);

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    try {
                        message.writeTo(os);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String fg = os.toString();
                    System.out.println(fg);
                    return ("+OK\r\n" + os.toString() + "\r\n.");

                }
                numOfMes++;

            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            messageString = "-ERR no such message";
            log.error(e.getMessage());
        }

        return messageString;
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
            if (getNumberOfMessages(user) + 1 >= number) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT message FROM mailbox WHERE username='" + user + "'");

                while (resultSet.next()) {
                    list.add(resultSet.getString(1));
                }

                String message = list.get(number - 1);

                PreparedStatement stUpdate = connection.prepareStatement("UPDATE mailbox SET deleted=true WHERE username='" + user + "' AND message='" + message + "' AND deleted=false;");

                stUpdate.execute();

                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return false;
    }

    public void insertFromDeletebox(String user) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE mailbox SET deleted=false WHERE username='" + user + "' AND deleted=true;");

            statement.execute();
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
