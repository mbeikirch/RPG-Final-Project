import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class GameClient extends JFrame
{
   //Attributes
   private JTextArea jtaMessages;
   private JTextField jtfSendMessage;
   private String ipAddr = "localhost";
   private int port = 4444;
   private Fighter myFighter;
   Integer clientTurnNumber = new Integer(1);
   private Socket client;

   //arrays to hold JLabels for all of the class info(health/names/pictures)
                                       //boss              //first      //second      //third
   private JLabel[] fighterHealths  = {new JLabel(""), new JLabel(""), new JLabel(""), new JLabel("")};
   private JLabel[] fighterPictures = {new JLabel(""), new JLabel(""), new JLabel(""), new JLabel("")};
   private JLabel[] playerNames     = {new JLabel(" Connected Players: "), new JLabel(""), new JLabel(""), new JLabel("")};

   private JButton jbAbility1 = new JButton();
   private JButton jbAbility2 = new JButton();
   private JButton jbAbility3 = new JButton();
   private JButton jbAbility4 = new JButton();

   private JButton[] abilities = {jbAbility1, jbAbility2, jbAbility3, jbAbility4};

   private Vector<Fighter> clientList = new Vector<Fighter>();

   ObjectOutputStream oos;
   ObjectInputStream ois;

   public static void main(String[] args)
   {
      new GameClient("localhost", 4444, new Warrior("TESTING"));
   }
   //Constructor
   public GameClient(String _ipAddr, int _port, Fighter _myFighter)
   {
      myFighter = _myFighter;
      ipAddr = _ipAddr;
      port = _port;

      clientList.add(new Diablo());
      clientList.add(new Warrior("SDKFJ"));
      clientList.add(new Wizard("ASD"));
      clientList.add(new Rogue("BLA"));

      setTitle("RPG Client");
      setResizable(false);

      //holds the player list
      JPanel jpPlayerList = new JPanel(new GridLayout(0,1));
      for(JLabel a : playerNames) { jpPlayerList.add(a); }

      //holds the fight area. For now just placeholder images for each player and the boss
      JPanel jpFightArea = new JPanel(new FlowLayout());
      jpFightArea.setBorder(BorderFactory.createLineBorder(Color.black,5, false));  //color, thickness of border, rounded edges

         //panel to hold the boss
         JPanel jpBossArea = new JPanel(new GridLayout(2,2,50,30));
            jpBossArea.add(fighterHealths[0]);
            fighterHealths[0].setVerticalAlignment(SwingConstants.BOTTOM);
            jpBossArea.add(new JLabel());
            jpBossArea.add(fighterPictures[0]);
            jpBossArea.add(new JLabel());
         jpFightArea.add(jpBossArea);

         //panel for the players, and adding the jlabels for all their info
         JPanel jpFighterArea = new JPanel(new GridLayout(3,2,0,5));
         for(int i=1; i < fighterHealths.length; i++)
         {
            jpFighterArea.add(fighterPictures[i]);
            jpFighterArea.add(fighterHealths[i]);
         }
         jpFightArea.add(jpFighterArea);

      //holds the sending info panel, abilities panel, and text box
      JPanel jpBottom = new JPanel(new GridLayout(0,2));
         jtaMessages = new JTextArea(6,0);
         jtaMessages.setLineWrap(true);
         jtaMessages.setWrapStyleWord(true);
         jtaMessages.setEditable(false);
         JScrollPane jspText = new JScrollPane(jtaMessages);
         jpBottom.add(jspText);

      //holds the user text field/send button and the ability buttons
      JPanel jpBottomRight = new JPanel(new GridLayout(2,0));

         //holds the ability buttons
         JPanel jpAbilities = new JPanel(new FlowLayout());

         //making the action listener for the abilities to use
         AbilityListener abilityCaster = new AbilityListener();

         for(int i=0; i < abilities.length; i++)
         {
            abilities[i].setText(myFighter.getAbilityName(i+1));
            abilities[i].setToolTipText("<html>" + myFighter.getAbilityDescription(i+1) + "</html>");
            abilities[i].addActionListener(abilityCaster);

            jpAbilities.add(abilities[i]);
         }
         jpBottomRight.add(jpAbilities);

      //holds the user text field/send button
      JPanel jpSendingInfo = new JPanel(new FlowLayout());
         jtfSendMessage = new JTextField(25);
         jpSendingInfo.add(jtfSendMessage);
         JButton jbSend = new JButton("Send");
         jbSend.addActionListener(new SendButtonListener());
         jpSendingInfo.add(jbSend);
         jpBottomRight.add(jpSendingInfo);

      jpBottom.add(jpBottomRight);

      add(jpFightArea, BorderLayout.CENTER);
      add(jpPlayerList, BorderLayout.EAST);
      add(jpBottom, BorderLayout.SOUTH);

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
      jtfSendMessage.requestFocus();

      connectToServer();

      try
      {
         oos.writeObject(myFighter);
         oos.flush();

         // TODO: 12/3/2015 Receive back the vector with yourself added 
      }
      catch(IOException ioe){ ioe.printStackTrace(); }

      Thread inputThread = new Thread(new ReceiveObjects());
      inputThread.start();

      updateGUI();
      setButtonsEnabled(false);
   }//end constructor

   //Methods

   private void connectToServer()
   {
      try
      {
         client = new Socket(ipAddr, port);

         oos = new ObjectOutputStream(new DataOutputStream(client.getOutputStream()));
         ois = new ObjectInputStream(new DataInputStream(client.getInputStream()));
      }
      catch(IOException ioe) { ioe.printStackTrace(); }
   }

   private void updateGUI()
   {
      for(int i=1; i < clientList.size(); i++)
      {
         playerNames[i].setHorizontalAlignment(SwingConstants.CENTER);
         playerNames[i].setText("<html>" + clientList.get(i).getName() + "<br> Class - " + clientList.get(i).getClassName() + "</html>");
      }
      for(int i=0; i < clientList.size(); i++)
      {
         fighterHealths[i].setText("Health: " + clientList.get(i).getCurrentHealth() + "/" + clientList.get(i).getBaseHealth());
         fighterPictures[i].setIcon(clientList.get(i).getIcon());
      }
      pack();
   }

   private void setButtonsEnabled(boolean b)
   {
      for(JButton myButton: abilities)
      {
         myButton.setEnabled(b);
      }
   }

   private int getClientTurn()
   {
      return clientTurnNumber;
   }


   public class SendButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {
         try
         {
            String message = myFighter.getName() + ": " + jtfSendMessage.getText();
            oos.writeObject(message);
            oos.flush();
            setButtonsEnabled(true);
         }
         catch(IOException ioe){ ioe.printStackTrace(); }

         jtfSendMessage.setText("");
         jtfSendMessage.requestFocus();
      }
   }//end inner class 1 (action listeners)

   public class AbilityListener implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {
         Object choice = ae.getSource();

         if(choice == jbAbility1)
         {
            jtaMessages.append(myFighter.getName() + " attacked for " + myFighter.ability1() + " damage!\n");
         }
         else if(choice == jbAbility2)
         {
            jtaMessages.append(myFighter.getName() + " healed for " + myFighter.ability2() + " hp!\n");
         }
         else if(choice == jbAbility3)
         {
            jtaMessages.append(myFighter.getName() + "knows that he is turn number" + getClientTurn());
            try {

               oos.writeObject(clientTurnNumber);
               oos.flush();

            }
            catch(IOException ioe){ ioe.printStackTrace(); }
         }
         else if(choice == jbAbility4)
         {

         }
      }
   }//end inner class 2 (action listeners)

   //tweaked this so we can use one stream for everything and just check which type of data came in I know we previously
   //were planning to use multiple streams but I was running into issues with creating more than one I/O stream per socket
   public class ReceiveObjects implements Runnable
   {
      public void run()
      {
         Object obj;
         try
         {
            while(((obj = ois.readObject()) != null))
            {
               if(obj instanceof String)
               {
                  jtaMessages.append((String)obj +"\n");
               }
               if(obj instanceof Vector)
               {
                  clientList = (Vector)obj;
                  System.out.println("got a vector back");
                  System.out.println(clientList.get(0).getCurrentHealth());
               }
               if(obj instanceof Integer)
               {
                  System.out.println(myFighter.getName()+ "'s turn has just ended");
                  clientTurnNumber++;
               }

            }
         }
         catch(IOException ioe) { ioe.printStackTrace(); }
         catch(ClassNotFoundException cnfe){ cnfe.printStackTrace(); }
      }
   }//end inner class 3 (threadz)
}//end class