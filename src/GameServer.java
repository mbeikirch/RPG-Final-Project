import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer
{
   final int PORT = 4444;
   final int serverTurnNumber = 0;
   int turn = 1;
   Integer clientTurnNumber = new Integer(1);
   static boolean emptyGame = true;

   //holds all of the object writers to be used
   private Vector<ObjectOutputStream> clientWriteList = new Vector<>();

   //holds all of the fighter objects(boss/4 players)
   private Vector<Fighter> clientList = new Vector<>();


   protected Fighter bossMan = new Diablo();

   public static void main(String[] args)
   {
      new GameServer();
   }
   
   public GameServer()
   {
      ServerSocket server = null;

      clientList.add(bossMan);

      try
      {
         System.out.println("Server started on IP: " + InetAddress.getLocalHost());
         server = new ServerSocket(PORT);
      }
      catch(BindException be){ System.out.println("Server already running on this port..."); }
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
      ObjectInputStream clientBuffer;
      
      public ClientHandler(Socket _cs)
      {
         cs = _cs;
         
         try {
            if(emptyGame == false){
               System.out.println("Game is full can not accept anymore connections!!");
            }
            else if (emptyGame) {
               //add an object reader for each client as they connect
               clientBuffer = new ObjectInputStream(new DataInputStream(cs.getInputStream()));

               //add client to the object writer vector
               clientWriteList.add(new ObjectOutputStream(new DataOutputStream(cs.getOutputStream())));

               //add the entire fighter object to the clientList vector
               clientList.add((Fighter) clientBuffer.readObject());

               System.out.println(clientList.get(1).getClass());
               System.out.println(clientList.get(1).getName());
               System.out.println(clientList.get(0).getClass());
               System.out.println(clientList.get(0).getCurrentHealth());

               if(turn == 3){
                  emptyGame = false;
               }
            }

         }
         catch(IOException ioe){ ioe.printStackTrace(); }
         catch(ClassNotFoundException cnfe){ cnfe.printStackTrace(); }
         catch(NullPointerException npe){ npe.printStackTrace(); }
      }
      
      public void run()
      {
         Object obj;
         
         try
         {
            while((obj = clientBuffer.readObject()) != null)
            {
               if(obj instanceof String)
               {
                  broadcastChatToClients((String)obj);

                  System.out.println("got a string");
               }

               if(obj instanceof Vector)
               {
                  System.out.println("got a vector");

                  clientList = (Vector)obj;
                  broadcastFighterListToClients();

                  System.out.println(clientList.get(0).getCurrentHealth());
               }
               if(obj instanceof Integer) {
                     System.out.println("turn number increased");
                     broadcastTurnNumberToClients((Integer) clientTurnNumber);

               }
            }
         }
         catch(IOException ioe) { ioe.printStackTrace(); }
         catch(ClassNotFoundException cnfe){ cnfe.printStackTrace(); }
      }


      public void broadcastChatToClients(String message)
      {
         //print out the received message to each client that is connected
         for(ObjectOutputStream oos : clientWriteList)
         {
            try
            {
               oos.writeObject(message);
               oos.flush();
            }
            catch(IOException ioe){ ioe.printStackTrace(); }
         }
      }

      public void broadcastFighterListToClients()
      {
         //print out the updated client list to each client
         for(ObjectOutputStream oos : clientWriteList)
         {
            try
            {
               oos.writeObject(clientList);
               oos.flush();
            }
            catch(IOException ioe){ ioe.printStackTrace(); }
         }
      }//End broadcastFighter to client method

      public void broadcastTurnNumberToClients(Integer turnNum)
      {
         for(ObjectOutputStream oos : clientWriteList)
         try
         {
            {
               oos.writeObject(turnNum);
               oos.flush();
            }
         }
         catch(IOException ioe){ ioe.printStackTrace(); }
         }
   }//End class Client Handler
}//End class file (GameServer)