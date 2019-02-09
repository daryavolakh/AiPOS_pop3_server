/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aipos_pop3_server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author Asus
 */
  /*Этот класс обрабатывает данные, получаемые сервером от клиента через одно сокетное соединение*/
  class ServerHandler implements Runnable{
      private Socket incoming;
      
      public ServerHandler(Socket incomingSocket){
          incoming = incomingSocket;
      }
      
      public void run(){
          try(InputStream inStream = incoming.getInputStream();
              OutputStream outStream = incoming.getOutputStream()){
                Scanner in = new Scanner(inStream, "UTF-8");
                PrintWriter out = new PrintWriter(
                new OutputStreamWriter(outStream, "UTF-8"), true /* auto cleanning*/);
                out.println("HELLO, ALESYA! Enter quit to exit! ");
                //передать обратно данные, которые получили от клиента
                      boolean done = false;
                      while(!done && in.hasNextLine())
                      {
                          String line = in.nextLine();
                          if (line.trim().equals("quit") || line.trim().equals("QUIT")) {
                              done = true;
                              //this.quit();
                              break;
                          }
                         }
          }
          
          catch (IOException exception){
              System.out.println("Something went wrong");
          }
      }
  }