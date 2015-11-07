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
      setSize(800,600);

      JPanel jpChatBox = new JPanel(new GridLayout(0,1));
      JPanel jpFightArea = new JPanel(new FlowLayout());

      jtaMessages = new JTextArea(20,30);
      jtaMessages.setLineWrap(true);
      jtaMessages.setWrapStyleWord(true);
      jtaMessages.setEditable(false);

      JScrollPane jspText = new JScrollPane(jtaMessages);
      jpChatBox.add(jspText);

      JPanel jpSendingInfo = new JPanel();
      jtfSendMessage = new JTextField(25);
      jpSendingInfo.add(jtfSendMessage);
      JButton jpSend = new JButton("Send");
      jpSend.addActionListener(new SendButtonListener());
      jpSendingInfo.add(jpSend);

      jpChatBox.add(jpSendingInfo);
      add(jpFightArea, BorderLayout.NORTH);
      add(jpChatBox, BorderLayout.SOUTH);

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
      jtfSendMessage.requestFocus();

      connectToServer();

      Thread brInputThread = new Thread(new ReceiveMessages());
      brInputThread.start();
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
