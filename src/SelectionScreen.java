/**
 * Created by Josh
 */
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class SelectionScreen
{
   private JLabel bla;

   public SelectionScreen()
   {
      JFrame clsFrame = new JFrame("Class Selection Screen");
      clsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      clsFrame.setSize(500,300);

      Font titleFont = new Font("Book Antiqua", Font.BOLD, 24);
      Font statsFont = new Font("Apple Casual", Font.PLAIN, 14);

      //making an arraylist to hold copies of each class in order to pull info from them(stats, abilities, etc.)
      ArrayList<Player> classList = new ArrayList<Player>();
      classList.add(new Warrior("Warrior"));
      classList.add(new Wizard("Wizard"));
      classList.add(new Rogue("Rogue"));

      //holds the class info(type, stats, abilities, etc.)
      JPanel classInfoPanel = new JPanel();

      //will hold the next/prev buttons, the name text field, and the create button
      JPanel bottomPanel = new JPanel();

      //textfield to hold the player name
      JTextField jtfName = new JTextField("");
      bottomPanel.add(jtfName);

      JButton jbPrev = new JButton("<=");
      bottomPanel.add(jbPrev);

      JButton jbNext = new JButton("=>");
      bottomPanel.add(jbNext);

      clsFrame.add(classInfoPanel, BorderLayout.CENTER);
      clsFrame.add(bottomPanel, BorderLayout.SOUTH);
      clsFrame.setLocationRelativeTo(null);
      clsFrame.setVisible(true);
   }

   private void updateText()
   {

   }
}