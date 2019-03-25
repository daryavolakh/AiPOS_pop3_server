package com.mycompany.aipos_pop3_server;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Алеся
 */
public class DataBaseTest {

    public DataBaseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getNumberOfMessages method, of class DataBase.
     */
    @Test
    public void testGetNumberOfMessages() {
        System.out.println("getNumberOfMessages");
        String username = "daryavolakh@gmail.com";
        DataBase instance = new DataBase();
        int expResult = 3;
        int result = instance.getNumberOfMessages(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllCharacters method, of class DataBase.
     */
    @Test
    public void testGetAllCharacters() {
        System.out.println("getAllCharacters");
        String username = "daryavolakh@gmail.com";
        DataBase instance = new DataBase();
        int expResult = 24;
        int result = instance.getAllCharacters(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessageInfo method, of class DataBase.
     */
    @Test
    public void testGetMessageInfo() {
        System.out.println("getMessageInfo");
        String username = "daryavolakh@gmail.com";
        DataBase instance = new DataBase();
        String expResult = "1 8\r\n2 8\r\n3 8\r\n.";
        String result = instance.getMessageInfo(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessages method, of class DataBase.
     */
    @Test
    public void testGetMessages() {
        System.out.println("getMessages");
        String username = "daryavolakh@gmail.com";
        DataBase instance = new DataBase();
        String expResult = "+OK\r\n1 message1\r\n2 message2\r\n3 message3\r\n.";
        String result = instance.getMessages(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessage method, of class DataBase.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        String username = "daryavolakh@gmail.com";
        int mesInd = 0;
        DataBase instance = new DataBase();
        String expResult = "+OK\r\n.";
        String result = instance.getMessage(username, mesInd);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessageRETR method, of class DataBase.
     */
    @Test
    public void testGetMessageRETR() throws Exception {
        System.out.println("getMessageRETR");
        String username = "daryavolakh@gmail.com";
        int mesInd = 0;
        DataBase instance = new DataBase();
        String expResult = "+OK\r\n";
        String result = instance.getMessageRETR(username, mesInd);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkUser method, of class DataBase.
     */
    @Test
    public void testCheckUser() {
        System.out.println("checkUser");
        String user = "daryavolakh";
        DataBase instance = new DataBase();
        boolean expResult = false;
        boolean result = instance.checkUser(user);
        assertEquals(expResult, result);
        
        user = "daryavolakh@gmail.com";
        expResult = true;
        result = instance.checkUser(user);
        user = "daryavolakh@gmail.com";
        expResult = true;
        result = instance.checkUser(user);
    }

    /**
     * Test of checkPass method, of class DataBase.
     */
    @Test
    public void testCheckPass() {
        System.out.println("checkPass");
        String user = "daryavolakh@gmail.com";
        String pass = "111";
        DataBase instance = new DataBase();
        boolean expResult = false;
        boolean result = instance.checkPass(user, pass);
        assertEquals(expResult, result);
        
        pass = "123123";
        expResult = true;
        result = instance.checkPass(user, pass);
        assertEquals(expResult, result);
    }

    /**
     * Test of findInTable method, of class DataBase.
     */
    @Test
    public void testFindInTable() {
        System.out.println("findInTable");
        String user = "daryavolakh@gmail.com";
        String message = "test";
        String table = "mailbox";
        DataBase instance = new DataBase();
        boolean expResult = false;
        boolean result = instance.findInTable(user, message, table);
        assertEquals(expResult, result);
        
        message = "test2";
        expResult = false;
        result = instance.findInTable(user, message, table);
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class DataBase.
     */
    
    @Test
    public void testDelete() {
        System.out.println("delete");
        String user = "daryavolakh@gmail.com";
        int number = 300;
        DataBase instance = new DataBase();
        boolean expResult = false;
        boolean result = instance.delete(user, number);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertFromDeletebox method, of class DataBase.
     */
    @Test
    public void testInsertFromDeletebox() {
        System.out.println("insertFromDeletebox");
        String user = "";
        DataBase instance = new DataBase();
        instance.insertFromDeletebox(user);
    }

    /**
     * Test of deleteAllFromDeletebox method, of class DataBase.
     */
    @Test
    public void testDeleteAllFromDeletebox() {
        System.out.println("deleteAllFromDeletebox");
        String user = "";
        DataBase instance = new DataBase();
        instance.deleteAllFromDeletebox(user);
    }
}
