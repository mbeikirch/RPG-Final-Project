import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


public class MainMenu extends JFrame implements ActionListener{
   
   //Attributes
   
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
   
   
   //Constructor
   public MainMenu()
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
   
   public static void main (String[]args)
   {
      new MainMenu();
   }//end main
   
   //Methods
   public void actionPerformed(ActionEvent ae)
   {
      //Getting sources
      Object choice = ae.getSource();
      
      if(choice == jbJoin)
      {
         System.exit(0);
      }
      else if(choice == jbLoad)
      {
         System.exit(0);
      }
      else if(choice == jbNew)
      {
         //just trying out making a warrior, this will actually need to be done through the selection screen, but whatevs
         Rogue test = new Rogue("Matt");

         System.out.println(test.description);

         System.out.println("Base Health: " + test.baseHealth + "\n" +
                            "Base Power: " + test.basePower);
         test.levelUp();
         System.out.println("Base Health: " + test.baseHealth+ "\n" +
                            "Base Power: " + test.basePower);

         System.out.println(test.getInfo());
      }
      else if(choice == jbExit)
      {
         System.exit(0);
      }
      
   }// end action performed
   
}//end class