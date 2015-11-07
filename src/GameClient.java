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

   private Icon background = new ImageIcon("test.png");

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

      JPanel jpChatBox = new JPanel(new GridLayout(0,1));

      JPanel jpPlayerList = new JPanel(new GridLayout(0,1));
      JLabel name1 = new JLabel("Matt");
      JLabel name2 = new JLabel("Zihao");
      JLabel name3 = new JLabel("Nicholas Lightburn");
      JLabel name4 = new JLabel("Josh");
      jpPlayerList.add(name1);
      jpPlayerList.add(name2);
      jpPlayerList.add(name3);
      jpPlayerList.add(name4);

      JPanel jpFightArea = new JPanel(new GridLayout());
      JButton bla = new JButton();
      bla.setIcon(background);
      jpFightArea.add(bla);

      jtaMessages = new JTextArea(10,10);
      jtaMessages.setLineWrap(true);
      jtaMessages.setWrapStyleWord(true);
      jtaMessages.setEditable(false);

      JScrollPane jspText = new JScrollPane(jtaMessages);
      jpChatBox.add(jspText);

      JPanel jpSendingInfo = new JPanel(new FlowLayout());
      jtfSendMessage = new JTextField(25);
      jpSendingInfo.add(jtfSendMessage);
      JButton jpSend = new JButton("Send");
      jpSend.addActionListener(new SendButtonListener());
      jpSendingInfo.add(jpSend);

      jpChatBox.add(jpSendingInfo);
      add(jpFightArea, BorderLayout.CENTER);
      add(jpPlayerList, BorderLayout.EAST);
      add(jpChatBox, BorderLayout.SOUTH);

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
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
