import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.net.*;
import java.util.*;

public class GameClient extends JFrame
{
   //Attributes
   JTextArea jtaMessages;
   JTextField jtfSendMessage;
   static String IP_ADDR;
   private final int PORT = 4444;
   Socket client;
   BufferedReader br;
   PrintWriter pw;
   Player myFighter;

   private Icon boss = new ImageIcon(".\\media\\boss1.png");
   private Icon warrior = new ImageIcon(".\\media\\warrior.png");
   private Icon wizard = new ImageIcon(".\\media\\wizard.png");
   private Icon rogue = new ImageIcon(".\\media\\rogue.png");

   public static void main(String[] args)
   {
      /**
      if( args.length == 1)
      {
         IP_ADDR = args[0];
      }
      else
      {
         System.out.println("No IP address on command line, using localhost.");
         System.out.println("Usage: java GameClient <IPAddress>");
         IP_ADDR = "localhost";
      }**/
      IP_ADDR = "localhost";
      new GameClient();
   }//end main

   //Constructor
   public GameClient()
   {
/*      //Testing code for choosing class
      String className = JOptionPane.showInputDialog(null, "Type in class(testing only)");

      if(className.equals("Warrior"))
      {
         myFighter = new Warrior("Warrior");
      }
      else if(className.equals("Wizard"))
      {
         myFighter = new Wizard("Wizard");
      }
      else if(className.equals("Rogue"))
      {
         myFighter = new Rogue("Rogue");
      }*/

      myFighter = new Warrior("Matt-TESTONLY");

      setTitle("RPG Client");
      setResizable(false);

      //holds the player list
      JPanel jpPlayerList = new JPanel(new GridLayout(0,1));

      JLabel name1 = new JLabel(myFighter.getInfo());
      JLabel name2 = new JLabel(myFighter.getInfo());
      JLabel name3 = new JLabel(myFighter.getInfo());
      JLabel name4 = new JLabel(myFighter.getInfo());

      jpPlayerList.add(name1);
      jpPlayerList.add(name2);
      jpPlayerList.add(name3);
      jpPlayerList.add(name4);

      //holds the fight area. For now just placeholder images for each player and the boss
      JPanel jpFightArea = new JPanel(new FlowLayout());
      jpFightArea.setBorder(BorderFactory.createLineBorder(Color.black,5, true));  //color, thickness of border, rounded edges

         //panel to hold the boss
         JPanel jpBossArea = new JPanel(new GridLayout(1,0,100,0));
            JLabel jlBossImage = new JLabel();
            jlBossImage.setIcon(boss);
            jpBossArea.add(jlBossImage);
            jpBossArea.add(new JLabel()); //this label is just a filler to properly space out the fighters/boss
         jpFightArea.add(jpBossArea);

         JPanel jpFighterArea = new JPanel(new GridLayout(3,2,0,5));
            JLabel jlPlayer1 = new JLabel();
            jlPlayer1.setIcon(warrior);
            JLabel jlPlayer1Health = new JLabel();
            jlPlayer1Health.setText("Health: 10/10");
            jpFighterArea.add(jlPlayer1Health);
            jpFighterArea.add(jlPlayer1);

            JLabel jlPlayer2 = new JLabel();
            jlPlayer2.setIcon(wizard);
         JLabel jlPlayer2Health = new JLabel();
         jlPlayer2Health.setText("Health: 10/10");
         jpFighterArea.add(jlPlayer2Health);
            jpFighterArea.add(jlPlayer2);

            JLabel jlPlayer3 = new JLabel();
            jlPlayer3.setIcon(rogue);
         JLabel jlPlayer3Health = new JLabel();
         jlPlayer3Health.setText("Health: 10/10");
         jpFighterArea.add(jlPlayer3Health);
            jpFighterArea.add(jlPlayer3);

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
         JPanel jpAbilities = new JPanel(new GridLayout());

         //making the action listener for the abilities to use
         AbilityListener abilityCaster = new AbilityListener();

         JButton jbAbility1 = new JButton("Ability 1");
         jbAbility1.setToolTipText("Ability 1 info");
         jbAbility1.addActionListener(abilityCaster);

         JButton jbAbility2 = new JButton("Ability 2");
         jbAbility2.setToolTipText("Ability 2 info");
         jbAbility2.addActionListener(abilityCaster);

         JButton jbAbility3 = new JButton("Ability 3");
         jbAbility3.setToolTipText("Ability 3 info");
         jbAbility3.addActionListener(abilityCaster);

         JButton jbAbility4 = new JButton("Ability 4");
         jbAbility4.setToolTipText("Ability 4 info");
         jbAbility4.addActionListener(abilityCaster);

         jpAbilities.add(jbAbility1);
         jpAbilities.add(jbAbility2);
         jpAbilities.add(jbAbility3);
         jpAbilities.add(jbAbility4);
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

      //this is where I try and set the background image, the testComponent class is below
      //testComponent bla = new testComponent();
      //bla.add(jpFightArea);

      add(jpFightArea, BorderLayout.CENTER);
      add(jpPlayerList, BorderLayout.EAST);
      add(jpBottom, BorderLayout.SOUTH);

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
      jtfSendMessage.requestFocus();

      connectToServer();

      Thread brInputThread = new Thread(new ReceiveMessages());
      brInputThread.start();

      pack();
   }//end constructor

   //Methods

   private void connectToServer()
   {
      try
      {
         client = new Socket(IP_ADDR, PORT);

         br = new BufferedReader(new InputStreamReader(client.getInputStream()));
         pw = new PrintWriter(client.getOutputStream());

         //once connected, send over character info(name, level, class)
         pw.println(myFighter.getInfo());
         pw.flush();
      }
      catch(IOException ioe) { ioe.printStackTrace(); }
   }

   public class SendButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {
         pw.println(myFighter.getName() + ": " + jtfSendMessage.getText());
         pw.flush();

         jtfSendMessage.setText("");
         jtfSendMessage.requestFocus();
      }
   }//end inner class 1 (action listeners)

   public class AbilityListener implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {
         String abilityChoice = ae.getActionCommand();

         if(abilityChoice.equals("Ability 1"))
         {
            jtaMessages.append(myFighter.getName() + " attacked for " + myFighter.ability1() + " damage!\n");
         }
         else if(abilityChoice.equals("Ability 2"))
         {
            jtaMessages.append(myFighter.getName() + " healed for " + myFighter.ability2() + " hp!\n");
         }
      }
   }//end inner class 2 (action listeners)

   public class ReceiveMessages implements Runnable
   {
      public void run()
      {
         String message;
         try
         {
            while((message = br.readLine()) != null)
            {
               /*if(*/message.equals("You win!");
               /*{
                  //for later
               }*/

               jtaMessages.append(message + "\n");
            }
         }
         catch(IOException ioe) { ioe.printStackTrace(); }
      }
   }//end inner class 3 (threadz)

   /*
   Was messing around with trying to get a background image, sort of got it to work but it wasn't working properly.
   Ignoring for now, may try and use again later
   */
   public class testComponent extends JPanel
   {
      private Image background;

      public testComponent()
      {
         try
         {
            background = ImageIO.read(getClass().getResource("fightArea.png"));
         }
         catch (IOException e) { e.printStackTrace(); }
      }

      public void paintComponent(Graphics g)
      {
         g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
      }
   }

}//end class