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
public class QUITCommandTest {
    
    public QUITCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Test
    public void executeTest(){
        QUITCommand instance = new QUITCommand();        
        String expResult = "+OK dewey POP3 server signing off";
        String result = instance.execute("0","daryavolakh@gmail.com");
        assertEquals(expResult, result);        
    }
    
}
