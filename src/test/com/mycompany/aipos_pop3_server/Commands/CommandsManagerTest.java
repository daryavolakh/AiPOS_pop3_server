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
public class CommandsManagerTest {

    public CommandsManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testFindAndRun() {
        CommandsManager cm = new CommandsManager();
        String expResult = "-ERR no such message";
        String result = cm.findAndRun("LIST", "bla", "info");
        assertEquals(expResult, result);
        
        expResult = "-ERR invalid password";
        result = cm.findAndRun("PASS", "000", "daryavolakh@gmail,com");
        assertEquals(expResult, result);
    }

}
