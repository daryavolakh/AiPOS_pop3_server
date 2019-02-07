
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Asus
 */
public class Server {
    static int port = 110; //логирование добавить!!!

  public static void main(String[] args) throws IOException {
      //устанавливаем сокет на стороне сервера
    DataBase db = new DataBase();
    try (ServerSocket server = new ServerSocket(port))
    {
        System.out.println("Waiting for a client...");
        //ждём когда подключится клиент
       try (Socket client = server.accept())
       {
           InputStream inStream = client.getInputStream();
           OutputStream outStream = client.getOutputStream();
           
           try (Scanner in = new Scanner(inStream, "UTF-8"))
           {
               PrintWriter out = new PrintWriter(
               new OutputStreamWriter(outStream, "UTF-8"),
               true);
               
               out.println("HELLO, ALESYA! Enter HI! ");
               
               //передать обратно данные, которые получили от клиента
               boolean done = false;
               while(!done && in.hasNextLine())
               {
                   String line = in.nextLine();
                   out.println("Echo: " + line);
                   if (line.trim().equals("HI")) done = true;
                }
           }
       }
    }
  }
}