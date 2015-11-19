/**
 * Created by Josh
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class SelectionScreen
{
   private JTable jtClassInfo;
   private final int TABLE_WIDTH = 3;
   private final int TABLE_HEIGHT = 3;
   private final String[] columnNames = {"bla", "Base Stat", "On Level Up"};
   private final String[][] tableData = {{"987", "bla", "ble"}, {"sd","asdf","453"}};

   public SelectionScreen()
   {
      JFrame clsFrame = new JFrame("Class Selection Screen");
      clsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      clsFrame.setSize(500,300);

      Font titleFont = new Font("Book Antiqua", Font.BOLD, 24);
      Font statsFont = new Font("Verdana", Font.PLAIN, 14);

      //making an arraylist to hold copies of each class in order to pull info from them(stats, abilities, etc.)
      ArrayList<Player> classList = new ArrayList<Player>();
      classList.add(new Warrior("Warrior"));
      classList.add(new Wizard("Wizard"));
      classList.add(new Rogue("Rogue"));

      //holds the class info(type, stats, abilities, etc.)
      JPanel classInfoPanel = new JPanel(new FlowLayout());

         jtClassInfo = new JTable(tableData, columnNames);

         //making it so you cannot select the cells
         jtClassInfo.setColumnSelectionAllowed(false);
         jtClassInfo.setRowSelectionAllowed(false);
         jtClassInfo.setFocusable(false);

         //changing the look of the table
         jtClassInfo.setShowGrid(false);
         jtClassInfo.setFont(statsFont);

         classInfoPanel.add(new JScrollPane(jtClassInfo));

      //will hold the selection panel and the name panel
      JPanel bottomPanel = new JPanel(new GridLayout(2,0));

         JPanel namePanel = new JPanel(new FlowLayout());
            //textfield to hold the player name
            JTextField jtfName = new JTextField("Fighter Name",15);

            //clears the textfield when the user clicks in the name text field
            jtfName.addFocusListener(new FocusListener()
            {
               public void focusGained(FocusEvent e) { jtfName.setText(""); }

               public void focusLost(FocusEvent e) {}
            });

            namePanel.add(jtfName);

            //button to create the character that you selected
            JButton jbCreate = new JButton("Create!");
            namePanel.add(jbCreate);

         JPanel selectionPanel = new JPanel(new FlowLayout());
            JButton jbPrev = new JButton("<=");
            selectionPanel.add(jbPrev);

            JButton jbNext = new JButton("=>");
            selectionPanel.add(jbNext);

         bottomPanel.add(selectionPanel);
         bottomPanel.add(namePanel);

      clsFrame.add(classInfoPanel, BorderLayout.CENTER);
      clsFrame.add(bottomPanel, BorderLayout.SOUTH);
      clsFrame.setResizable(false);
      clsFrame.setLocationRelativeTo(null);
      clsFrame.setVisible(true);
   }

   private void updateText()
   {

   }
}