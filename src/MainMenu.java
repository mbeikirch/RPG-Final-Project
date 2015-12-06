import java.io.*;
import java.awt.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


public class MainMenu extends JFrame implements ActionListener{

   //Server Info panel
   public JPanel jpServerInfo;
   public JLabel jlServerIP;
   public JLabel jlPort;
   public JTextField jtfServerIP;
   public JTextField jtfPort;
   //Controls panel
   public JPanel jpButtons;
   public JButton jbJoin;
   public JButton jbLoad;
   public JButton jbNew;
   public JButton jbExit;

   private Fighter myFighter;

   public static void main (String[]args)
   {
      new MainMenu(new Warrior("Default"));
   }//end main

   //Constructor
   public MainMenu() { drawMenu(); }

   public MainMenu(Fighter _myFighter)
   {
      myFighter = _myFighter;
      System.out.println(myFighter.getName());
      drawMenu();
   }

   private void drawMenu()
   {
      //Panel 1
      jpServerInfo = new JPanel(new FlowLayout());
      jpServerInfo.add(jlServerIP = new JLabel("Server IP: "));
      jpServerInfo.add(jtfServerIP = new JTextField(5));
      jpServerInfo.add(jlPort = new JLabel("Port: "));
      jpServerInfo.add(jtfPort = new JTextField(5));
      //Insertion of panel 1
      add(jpServerInfo, BorderLayout.NORTH);

      //Panel 2
      jpButtons = new JPanel(new BorderLayout());
      jpButtons.add(jbJoin = new JButton("Join!"), BorderLayout.NORTH);
      jpButtons.add(jbLoad = new JButton("Load"), BorderLayout.WEST);
      jpButtons.add(jbNew = new JButton("New"), BorderLayout.EAST);
      jpButtons.add(jbExit = new JButton("Exit"), BorderLayout.SOUTH);
      //Insertion of panel 2
      add(jpButtons, BorderLayout.SOUTH);

      //Adding action listeners
      jbJoin.addActionListener(this);
      jbLoad.addActionListener(this);
      jbNew.addActionListener(this);
      jbExit.addActionListener(this);

      //General stuff for frames
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
      pack();
   }
   
   //Methods
   public void actionPerformed(ActionEvent ae)
   {
      //Getting sources
      Object choice = ae.getSource();
      
      if(choice == jbJoin)
      {
         String ipAddr = jtfServerIP.getText();
         String port = jtfPort.getText();

         try
         {
            if(ipAddr.equals("") || port.equals(""))
            {
               JOptionPane.showMessageDialog(jbJoin, "You must enter a valid IP address and port number.");
            }
            else
            {
               Socket s = new Socket(ipAddr, Integer.parseInt(port));
               s.close();

               new GameClient(ipAddr, Integer.parseInt(port), myFighter);
               dispose();
            }
         }
         catch(NullPointerException npe){ JOptionPane.showMessageDialog(jbJoin, "You must select a character."); }
         catch(IOException ioe){ JOptionPane.showMessageDialog(jbJoin, "Couldn't connect to server."); }

      }
      else if(choice == jbLoad)
      {
         System.exit(0);
      }
      else if(choice == jbNew)
      {
         new SelectionScreen();
      }
      else if(choice == jbExit)
      {
         System.exit(0);
      }
      
   }// end action performed
   
}//end class