/**
 * Created by Josh
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextAttribute;
import java.rmi.activation.ActivationInstantiator;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class SelectionScreen implements ActionListener
{
   private final int STATS_ROWS = 3;
   private final int STATS_COLUMNS = 3;
   private final int ABILITIES_ROWS = 5;
   private final int ABILITES_COLUMNS = 2;
   private final int TABLE_PADDING = 7; //x & y padding

   private int listPosition;

   private ArrayList<String[]> statList;

   //absolutely terrible way to do this, but currently using arrays of jlabels to display the classInfo
   private JLabel[][] jlStats = new JLabel[STATS_ROWS][STATS_COLUMNS];
   private JLabel[][] jlAbilities = new JLabel[ABILITIES_ROWS][ABILITES_COLUMNS];
   private JLabel className;

   // TODO: 11/20/2015 takeout when finished testing
   public static void main(String[] args) {
      new SelectionScreen();
   }

   public SelectionScreen()
   {
      JFrame clsFrame = new JFrame("Choose your class");
      clsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      Font titleFont = new Font("Book Antiqua", Font.BOLD, 20);
      Font statsDescFont = new Font("Arial", Font.ITALIC, 16);
      Font statsFont = new Font("Verdana", Font.PLAIN, 14);

      //making an arraylist to hold copies of each class in order to pull info from them(stats, abilities, etc.)
      ArrayList<Player> classList = new ArrayList<Player>();
      classList.add(new Warrior("Warrior"));
      classList.add(new Wizard("Wizard"));
      classList.add(new Rogue("Rogue"));

      listPosition = classList.size() - 1;

      statList = new ArrayList<String[]>();
      for(Player i : classList)
      {
         statList.add(i.getDescription().split(","));
      }

      //holds the class info(type, stats, abilities, etc.)
      JPanel classInfoPanel = new JPanel(new GridLayout(STATS_ROWS,STATS_COLUMNS,TABLE_PADDING,TABLE_PADDING));

         /*
         Here I'm creating and adding all of the jlabels. As of now, the main reason I did this was so we had flexibility
         with changing specific fonts/adding icons/etc. JTable is cleaner, but doesn't have row headers by default. If anyone
         has a better way to do this, feel free to change it
         */
         for(int i=0; i<STATS_ROWS; i++)
         {
            for(int j=0; j<STATS_COLUMNS; j++)
            {
               JLabel label = new JLabel();
               label.setFont(statsFont);
               label.setHorizontalAlignment(SwingConstants.CENTER);

               jlStats[i][j] = label;
               classInfoPanel.add(label);
            }
         }

         //making a label for the class name
         className = new JLabel();
         className.setFont(titleFont);
         className.setHorizontalAlignment(SwingConstants.CENTER);

         //changing the stat description text to the appropriate values
         jlStats[1][0].setText("Power");
         jlStats[1][0].setFont(statsDescFont);

         jlStats[2][0].setText("Health");
         jlStats[2][0].setFont(statsDescFont);

         jlStats[0][1].setText("Base");
         jlStats[0][1].setFont(statsDescFont);

         jlStats[0][2].setText("On Level Up");
         jlStats[0][2].setFont(statsDescFont);

         updateText();

      //holds the class name and a divider
      JPanel classNamePanel = new JPanel(new GridLayout(0,1,TABLE_PADDING, TABLE_PADDING));
         classNamePanel.add(className);
         classNamePanel.add(new JSeparator(SwingConstants.HORIZONTAL));

      //holds the ability descriptions
      JPanel abilityPanel = new JPanel(new GridLayout(ABILITIES_ROWS, ABILITES_COLUMNS));

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
            jbCreate.addActionListener(this);
            namePanel.add(jbCreate);

         JPanel selectionPanel = new JPanel(new FlowLayout());
            JButton jbPrev = new JButton("<=");
            jbPrev.addActionListener(this);
            selectionPanel.add(jbPrev);

            JButton jbNext = new JButton("=>");
            jbNext.addActionListener(this);
            selectionPanel.add(jbNext);

         bottomPanel.add(selectionPanel);
         bottomPanel.add(namePanel);

      //adding all of the panels to the jframe
      clsFrame.add(classNamePanel, BorderLayout.NORTH);
      clsFrame.add(classInfoPanel, BorderLayout.WEST);
      clsFrame.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER); //divider between the stats and ability info
      clsFrame.add(abilityPanel, BorderLayout.EAST);
      clsFrame.add(bottomPanel, BorderLayout.SOUTH);

      clsFrame.setResizable(false);
      clsFrame.pack();
      clsFrame.setLocationRelativeTo(null);
      clsFrame.setVisible(true);
   }

   //updates the text to reflect the proper class stats/abilities
   private void updateText()
   {
      className.setText(statList.get(listPosition)[0]);

      jlStats[1][1].setText(statList.get(listPosition)[1]);
      jlStats[1][2].setText("+" + statList.get(listPosition)[2]);

      jlStats[2][1].setText(statList.get(listPosition)[3]);
      jlStats[2][2].setText("+" + statList.get(listPosition)[4]);

      // TODO: 11/21/2015 add in code for updating ability info
   }

   public void actionPerformed(ActionEvent ae)
   {
      String choice = ae.getActionCommand();

      //The choices endlessly cycle rather than stopping at either end
      if(choice == "<=")
      {
         if(listPosition == 0)
         {
            listPosition = statList.size() - 1; //endless cycle
         }
         else
         {
            listPosition--;
         }
         updateText();
      }

      else if(choice == "=>")
      {
         if(listPosition == statList.size() - 1) //endless cycle
         {
            listPosition = 0;
         }
         else
         {
            listPosition++;
         }
         updateText();
      }

      else if (choice == "Create!")
      {
         System.exit(0);
         // TODO: 11/21/2015 set the class name to whatever the user entered, and write out(object output stream) the appropriate player
      }
   }
}