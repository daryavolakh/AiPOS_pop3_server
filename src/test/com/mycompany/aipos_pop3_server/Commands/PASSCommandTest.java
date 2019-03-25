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
public class PASSCommandTest {
    
    public PASSCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Test
    public void executeTest(){
        PASSCommand instance = new PASSCommand();
        String expResult = "-ERR invalid password";
        String result = instance.execute("111","daryavolakh@gmail.com");
        assertEquals(expResult, result);
        
        expResult = "+OK Pass accepted";
        result = instance.execute("123123","daryavolakh@gmail.com");
        assertEquals(expResult, result);        
    }
    
}
