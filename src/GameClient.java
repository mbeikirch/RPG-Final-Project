import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class GameClient extends JFrame
{
   JTextArea jtaMessages;
   JTextField jtfSendMessage;
   static String IP_ADDR;
   private final int PORT = 4444;
   Socket client;
   BufferedReader br;
   PrintWriter pw;

   private Icon background = new ImageIcon("..\\media\\fightArea.png");

   public static void main(String[] args)
   {
      if( args.length == 1)
      {
         IP_ADDR = args[0];
      }
      else
      {
         System.out.println("No IP address on command line, using localhost.");
         System.out.println("Usage: java GameClient <IPAddress>");
         IP_ADDR = "localhost";
      }
      new GameClient();
   }

   public GameClient()
   {
      setTitle("RPG Client");
      setResizable(false);

      //holds the player list
      JPanel jpPlayerList = new JPanel(new GridLayout(0,1));
         JLabel name1 = new JLabel("Matt");
         JLabel name2 = new JLabel("Zihao");
         JLabel name3 = new JLabel("Nicholas Lightburn");
         JLabel name4 = new JLabel("Josh");
         jpPlayerList.add(name1);
         jpPlayerList.add(name2);
         jpPlayerList.add(name3);
         jpPlayerList.add(name4);

      //holds the fight screen, just a place holder picture for now
      JPanel jpFightArea = new JPanel(new FlowLayout());
         JLabel bla = new JLabel();
         bla.setIcon(background);
         jpFightArea.add(bla);

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

      JButton jbAbility1 = new JButton("!");
      jbAbility1.setToolTipText("Ability 1 info");

      JButton jbAbility2 = new JButton("@");
      jbAbility2.setToolTipText("Ability 2 info");

      JButton jbAbility3 = new JButton("#");
      jbAbility3.setToolTipText("Ability 3 info");

      JButton jbAbility4 = new JButton("$");
      jbAbility4.setToolTipText("Ability 4 info");

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
      add(jpFightArea, BorderLayout.CENTER);
      add(jpPlayerList, BorderLayout.EAST);
      add(jpBottom, BorderLayout.SOUTH);

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocation(500,300);
      setVisible(true);
      jtfSendMessage.requestFocus();

      connectToServer();

      Thread brInputThread = new Thread(new ReceiveMessages());
      brInputThread.start();

      pack();
   }

   private void connectToServer()
   {
      try
      {
         client = new Socket(IP_ADDR, PORT);

         br = new BufferedReader(new InputStreamReader(client.getInputStream()));
         pw = new PrintWriter(client.getOutputStream());
      }
      catch(IOException ioe) { ioe.printStackTrace(); }
   }

   public class SendButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {
         pw.println("Matt: " + jtfSendMessage.getText());
         pw.flush();

         jtfSendMessage.setText("");
         jtfSendMessage.requestFocus();
      }
   }

   public class ReceiveMessages implements Runnable
   {
      public void run()
      {
         String message;
         try
         {
            while((message = br.readLine()) != null)
            {
               jtaMessages.append(message + "\n");
            }
         }
         catch(IOException ioe) { ioe.printStackTrace(); }
      }
   }
}
