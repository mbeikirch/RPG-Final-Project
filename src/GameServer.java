import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer
{
   final int PORT = 4444;
   Integer myTurnNumber = 0;
   Integer clientTurnNumber = 1;
   int numPlayers;

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

      //allows up to three players to connect
      while(numPlayers <= 3)
      {
         try
         {
            Socket client = server.accept();
            
            Thread ch = new ClientHandler(client);
            ch.start();
         }
         catch(IOException ioe){ ioe.printStackTrace(); }

         numPlayers++;
      }
   }
   
   class ClientHandler extends Thread
   {
      Socket cs;
      ObjectInputStream clientBuffer;
      ObjectOutputStream clientWriter;
      
      public ClientHandler(Socket _cs)
      {
         cs = _cs;

         try
         {
            //add an object reader for each client as they connect
            clientBuffer = new ObjectInputStream(new DataInputStream(cs.getInputStream()));
            clientWriter = new ObjectOutputStream(new DataOutputStream(cs.getOutputStream()));

            //add client to the object writer vector
            clientWriteList.add(clientWriter);

            //add the entire fighter object to the clientList vector
            clientList.add((Fighter)clientBuffer.readObject());
            broadcastFighterListToClients();
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
                  System.out.println("got a string");
                  broadcastChatToClients((String)obj);
               }
               //when a vector is received, update the server's copy of the vector and broadcast it out
               //then, increment the turn number and broadcast that out
               if(obj instanceof Vector)
               {
                  System.out.println("got a vector");

                  clientList = (Vector)obj;
                  broadcastFighterListToClients();

                  //if all the players have taken their turn(player 3 = turn 3) reset the turn counter to 0 and let the boss do stuff
                  if(clientTurnNumber == 3)
                  {
                     clientTurnNumber = 0;
                     doBossStuff();
                  }
                  else
                  {
                     clientTurnNumber++;
                     broadcastTurnNumberToClients();
                  }
               }
            }
         }
         catch(IOException ioe) { ioe.printStackTrace(); }
         catch(ClassNotFoundException cnfe){ cnfe.printStackTrace(); }
      }

      private void doBossStuff()
      {
         //boss AI goes here
         //once the boss is done, then broadcast
         //broadcastFighterListToClients();
         //broadcastTurnNumberToClients();
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
      }

      public void broadcastTurnNumberToClients()
      {
         for(ObjectOutputStream oos : clientWriteList)
         {
            try
            {
               oos.writeObject(clientTurnNumber);
               oos.flush();
            }
            catch(IOException ioe){ ioe.printStackTrace(); }
         }
      }
   }//End class Client Handler
}//End class file (GameServer)