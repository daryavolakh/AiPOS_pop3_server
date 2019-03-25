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
public class UIDLCommandTest {
    
    public UIDLCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Test
    public void executeTest(){
        UIDLCommand instance = new UIDLCommand();        
        String expResult = "+OK\r\n1 message1\r\n2 message2\r\n3 message3\r\n.";
        String result = instance.execute("0","daryavolakh@gmail.com");
        assertEquals(expResult, result);        
    }
    
}
