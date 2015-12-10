import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class GameClient
{
   //Attributes
   private JTextArea jtaMessages;
   private JTextField jtfSendMessage;
   private String ipAddr = "localhost";
   private int port = 4444;
   private Fighter myFighter;
   Integer myTurnNumber;
   private Socket client;
   Vector<Fighter> clientList;

   //arrays to hold JLabels for all of the class info(health/names/pictures)
                                       //boss              //first      //second      //third
   private JLabel[] fighterHealths  = {new JLabel(" "), new JLabel(" "), new JLabel(" "), new JLabel(" ")};
   private JLabel[] fighterPictures = {new JLabel(" "), new JLabel(" "), new JLabel(" "), new JLabel(" ")};
   private JLabel[] playerNames     = {new JLabel(" Connected Players: "), new JLabel(""), new JLabel(" "), new JLabel(" ")};

   private JButton jbAbility1 = new JButton();
   private JButton jbAbility2 = new JButton();

   private JButton[] abilities = {jbAbility1, jbAbility2};

   public JFrame myFrame;

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

      myFrame = new JFrame("RPG Client");

      //holds the player list
      JPanel jpPlayerList = new JPanel(new GridLayout(0,1));

      //add all of the jlabels to the connected players list
      for(JLabel a : playerNames) { jpPlayerList.add(a); }

      //holds the fight area
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

         //loop to set all of the button names/tooltips/add an action listener to them
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

      myFrame.add(jpFightArea, BorderLayout.CENTER);
      myFrame.add(jpPlayerList, BorderLayout.EAST);
      myFrame.add(jpBottom, BorderLayout.SOUTH);

      myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      myFrame.setLocationRelativeTo(null);
      myFrame.pack();
      myFrame.setVisible(true);
      jtfSendMessage.requestFocus();

      connectToServer();

      Thread inputThread = new Thread(new ReceiveObjects());
      inputThread.start();

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

         oos.writeObject(myFighter);
         oos.flush();
         System.out.println("wrote out myFighter");

         myTurnNumber = ois.readInt();
         System.out.println("Turn Number: " + myTurnNumber);
      }
      catch(IOException ioe) { ioe.printStackTrace(); }
      //catch(ClassNotFoundException cnfe){ cnfe.printStackTrace(); }
   }

   private void setButtonsEnabled(boolean b)
   {
      for(JButton myButton: abilities)
      {
         myButton.setEnabled(b);
      }
   }

   class SendButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {
         try
         {
            String message = myFighter.getName() + ": " + jtfSendMessage.getText();
            oos.writeObject(message);
            oos.flush();
         }
         catch(IOException ioe){ ioe.printStackTrace(); }

         jtfSendMessage.setText("");
         jtfSendMessage.requestFocus();
      }
   }//end inner class 1 (action listeners)

   class AbilityListener implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {
         try
         {
            Object choice = ae.getSource();

            if (choice == jbAbility1)
            {
               jtaMessages.append(clientList.get(1).getName() + " attacked " + clientList.get(0).getName() + " for " + myFighter.ability1() + " damage!\n");
               clientList.get(0).changeCurrentHealth(-myFighter.ability1());
            }
            else if (choice == jbAbility2)
            {
               jtaMessages.append(myFighter.getName() + " performed " + myFighter.getAbilityName(2) + " and healed for " + myFighter.ability2() + " hp!\n");
               clientList.get(1).changeCurrentHealth(myFighter.ability1());
            }

            oos.writeObject(clientList);
            oos.flush();
            setButtonsEnabled(false);
         }
         catch(IOException ioe){ ioe.printStackTrace(); }
      }
   }//end inner class 2 (action listeners)

   //tweaked this so we can use one stream for everything and just check which type of data came in I know we previously
   //were planning to use multiple streams but I was running into issues with creating more than one I/O stream per socket
   class ReceiveObjects implements Runnable
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
                  jtaMessages.append(obj +"\n");
               }
               else if(obj instanceof Vector)
               {
                  clientList = (Vector<Fighter>)obj;
                  updateGUI(clientList);
               }
               else if(obj instanceof Integer)
               {
                  System.out.println("got the turn");
                  if( ((Integer) obj).intValue() == (myTurnNumber.intValue()) )
                  {
                     System.out.println("my turn");
                     setButtonsEnabled(true);
                  }
               }
            }
         }
         catch(IOException ioe) { ioe.printStackTrace(); }
         catch(ClassNotFoundException cnfe){ cnfe.printStackTrace(); }
      }

      private void updateGUI(Vector<Fighter> clientList)
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
         myFrame.revalidate();
         myFrame.pack();
      }
   }//end inner class 3 (threadz)
}//end class