import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer
{
   final int PORT = 4444;
   Integer myTurnNumber = 0;
   Integer clientTurnNumber = 1;
   int numPlayers;
   Random rngAbility = new Random();

   public Fighter[] bossList = {new Diablo(), new DemonKing(), new Saitama()};
   //holds all of the object writers to be used
   private Vector<ObjectOutputStream> clientWriteList = new Vector<>();

   //holds all of the fighter objects(boss/4 players)
   private Vector<Fighter> clientList = new Vector<Fighter>();

   protected Fighter bossMan = new Fighter();


   public static void main(String[] args)
   {
      new GameServer();
   }
   
   public GameServer()
   {
      ServerSocket server = null;

      int bossNum = rngAbility.nextInt(2);
      clientList.add(bossList[bossNum]);


      try
      {
         System.out.println("Server started on IP: " + InetAddress.getLocalHost());
         server = new ServerSocket(PORT);
      }
      catch(BindException be){ System.out.println("Server already running on this port..."); }
      catch(UnknownHostException uhe){ uhe.printStackTrace(); } 
      catch(IOException ioe){ ioe.printStackTrace(); }

      //allows up to three players to connect
      while(numPlayers < 3)
      {
         try
         {
            Socket client = server.accept();
            
            Thread ch = new ClientHandler(client);
            ch.start();
            numPlayers++;
            System.out.println("Players: " + numPlayers);
         }
         catch(IOException ioe){ ioe.printStackTrace(); }
      }
      //close off server once three people have connected
      try
      {
         server.close();
      }
      catch(IOException ioe){ }
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
            //add client to the object writer vector
            clientWriter = new ObjectOutputStream(new DataOutputStream(cs.getOutputStream()));
            clientWriteList.add(clientWriter);

            //add an object reader for each client as they connect
            clientBuffer = new ObjectInputStream(new DataInputStream(cs.getInputStream()));

            //add the entire fighter object to the clientList vector
            clientList.add((Fighter)clientBuffer.readObject());
            System.out.println("Vector Size: " + clientList.size());
            clientWriter.writeInt(clientTurnNumber++);

            broadcastClientListToClients();

            if(numPlayers == 3)
            {
               broadcastClientListToClients();
               broadcastTurnNumberToClients();
            }
         }
         catch(EOFException | SocketException e)
         {
            System.out.println("got a tester");
            numPlayers--;
            return;
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

                  clientList = (Vector<Fighter>)obj;
                  broadcastClientListToClients();

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
         catch(SocketException se){ System.out.println("Client Disconnected"); }
         catch(IOException ioe) { ioe.printStackTrace(); }
         catch(ClassNotFoundException cnfe){ cnfe.printStackTrace(); }
         catch(NullPointerException npe){ }
      }

      private void doBossStuff()
      {
         //boss AI goes here
         int abilityNum = rngAbility.nextInt(2);
         if(abilityNum == 0){

            int clientNum = rngAbility.nextInt(2)+1;
            clientList.get(clientNum).changeCurrentHealth(-bossMan.ability1());

         }
         if(abilityNum == 1){

            bossMan.ability2();
            int clientNum = rngAbility.nextInt(2)+1;
            clientList.get(clientNum).changeCurrentHealth(+bossMan.ability2());

         }
         if(abilityNum == 2){

            //int clientNum = rngAbility.nextInt(2)+1;
            clientList.get(1).changeCurrentHealth(-bossMan.ability3());
            clientList.get(2).changeCurrentHealth(-bossMan.ability3());
            clientList.get(3).changeCurrentHealth(-bossMan.ability3());

         }

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

      public void broadcastClientListToClients()
      {
         //print out the updated client list to each client
         for(ObjectOutputStream oos : clientWriteList)
         {
            try
            {
               oos.writeObject(clientList);
               oos.flush();
            }
            catch(IOException ioe){ }
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