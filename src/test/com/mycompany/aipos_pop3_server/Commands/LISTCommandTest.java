/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aipos_pop3_server.Commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Asus
 */
public class LISTCommandTest {
    
    public LISTCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Test
    public void executeTest(){
        LISTCommand instance = new LISTCommand();        
        String expResult = "+OK 3 messages (24 octets)\r\n1 8\r\n2 8\r\n3 8\r\n.";
        String result = instance.execute("0","daryavolakh@gmail.com");
        assertEquals(expResult, result);        
    }
}
