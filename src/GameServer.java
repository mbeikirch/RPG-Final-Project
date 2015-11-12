import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer
{
   final int PORT = 4444;
   
   private Vector<PrintWriter> clientWriteList = new Vector<PrintWriter>();
   private Vector<String> clientList = new Vector<String>();
   protected Fighter bossMan = new Diablo();

   public static void main(String[] args)
   {
      new GameServer();
   }
   
   public GameServer()
   {
      ServerSocket server = null;
      
      try
      {
         System.out.println("Server started on IP: " + InetAddress.getLocalHost());
         server = new ServerSocket(PORT);
      }
      catch(UnknownHostException uhe){ uhe.printStackTrace(); } 
      catch(IOException ioe){ ioe.printStackTrace(); }
      
      while(true)
      {
         try
         {
            Socket client = server.accept();
            
            Thread ch = new ClientHandler(client);
            ch.start();
         }
         catch(IOException ioe){ ioe.printStackTrace(); }
      } 
   }
   
   class ClientHandler extends Thread
   {
      Socket cs;
      BufferedReader clientBuffer;
      
      public ClientHandler(Socket _cs)
      {
         cs = _cs;
         
         try
         {
            //add a reader for each client as they connnect
            clientBuffer = new BufferedReader(new InputStreamReader(cs.getInputStream()));

            //add client to the printwriter vector, allows chat to be sent to it
            clientWriteList.add(new PrintWriter(cs.getOutputStream()));

            //add info about the client( class, name, level, etc.) to the vector clientList
            String charInfo = clientBuffer.readLine();
            clientList.add(charInfo);
         }
         catch(IOException ioe){ ioe.printStackTrace(); }
      }
      
      public void run()
      {
         String message;
         
         try
         {
            while((message = clientBuffer.readLine()) != null)
            {
               System.out.println("New message: " + message);
               broadcastToClients(message);

               bossMan.changeCurrentHealth(-25);

               if(bossMan.currentHealth == 0)
               {
                  broadcastToClients("You win!");
               }
            }
         }
         catch(IOException ioe) { ioe.printStackTrace(); }
      }
      
      public void broadcastToClients(String message)
      {
         //print out the received message to each client that is connected
         for(PrintWriter pw : clientWriteList)
         {
            pw.println(message);
            pw.flush();
         }
      }
   }
}