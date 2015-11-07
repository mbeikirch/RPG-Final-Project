import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer
{
   final int PORT = 16789;
   
   private Vector<PrintWriter> clientWriteList = new Vector<PrintWriter>();
   
   public static void main(String[] args)
   {
      new ChatServer();
   }
   
   public ChatServer()
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
            
            Thread cmh = new ClientMessageHandler(client);
            cmh.start();
            
            System.out.println("New client connected!" + client);
         }
         catch(Exception e){ System.out.println("Literally anything could have happened."); }
      } 
   }
   
   class ClientMessageHandler extends Thread
   {
      Socket cs;
      BufferedReader clientBuffer;
      
      public ClientMessageHandler(Socket _cs)
      {
         cs = _cs;
         
         try
         {
            clientBuffer = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            
            clientWriteList.add(new PrintWriter(cs.getOutputStream()));
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
            }
         }
         catch(IOException ioe) { ioe.printStackTrace(); }
      }
      
      public void broadcastToClients(String message)
      {
//          Iterator it = clientWriteList.iterator();
//          
//          while(it.hasNext())
//          {
//             PrintWriter clientWriter = (PrintWriter) it.next();
//             clientWriter.println(message);
//             clientWriter.flush();
//          }
         for(PrintWriter pw : clientWriteList)
         {
            pw.println(message);
            pw.flush();
         }
      }
   }
}