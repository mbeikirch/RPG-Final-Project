import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatClient extends JFrame {
   
   private JTextArea message_area = null;
   private JTextField send_area = null;
   private JTextArea playerList = null;
   private String user_name = null;
   
   public static void main(String []args){
   
      ChatClient c = new ChatClient("Chat Window");    
   }
   
   //constructor
   public ChatClient(String s){
   
      super(s);
      this.setSize(800,600);
      this.setResizable(true);
      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
      message_area = new JTextArea();
      message_area.setEditable(false);
      this.add(message_area, "Center");
      message_area.setFont(new Font("Arial", Font.PLAIN, 16));

      playerList = new JTextArea();
      playerList.setEditable(false);
      //this.add(playerList, "East");
      playerList.setFont(new Font("Arial", Font.PLAIN, 16));
         
      JPanel p = new JPanel();
      p.setLayout(new FlowLayout());
      send_area = new JTextField(30);
      send_area.setFont(new Font("Arial", Font.PLAIN, 16));
         
      p.add(send_area);
      p.setBackground(new Color(221,221,221));
      JButton send = new JButton("Send");
      p.add(send);
      JButton clear = new JButton("Clear");
      clear.addActionListener(
            new ActionListener(){
               public void actionPerformed(ActionEvent ae){  
                  send_area.setText(null);
               }
            
            }); 
      p.add(clear);
      JButton start = new JButton("Start!");
      p.add(start);
         
      this.add(p, "South");
      this.setVisible(true);
      send_area.requestFocus();
   
   
   
   }
}
   
