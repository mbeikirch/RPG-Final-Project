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
   private final int ABILITIES_ROWS = 5;
   private final int ABILITES_COLUMNS = 2;
   private final int TABLE_PADDING = 1; //x & y padding

   private int listPosition;

   private ArrayList<Fighter> classList;

   //absolutely terrible way to do this, but currently using arrays of jlabels to display the classInfo
   private JLabel[][] jlAbilities = new JLabel[ABILITIES_ROWS][ABILITES_COLUMNS];
   private JLabel className;
   private JLabel baseHealth;
   private JTextField jtfName;
   private JFrame clsFrame;

   // TODO: 11/20/2015 takeout when finished testing
   public static void main(String[] args)
   {
      new SelectionScreen();
   }

   public SelectionScreen()
   {
      clsFrame = new JFrame("Choose your class");
      clsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      Font titleFont = new Font("Book Antiqua", Font.BOLD, 20);
      Font headerFont = new Font("Arial", Font.PLAIN, 17);
      Font statsFont = new Font("Verdana", Font.PLAIN, 14);

      //making an arraylist to hold copies of each class in order to pull info from them(stats, abilities, etc.)
      classList = new ArrayList<Fighter>();
      classList.add(new Warrior("Warrior"));
      classList.add(new Wizard("Wizard"));
      classList.add(new Rogue("Rogue"));

      listPosition = classList.size() - 1;

      //making a label for the class name
      className = new JLabel();
      className.setFont(titleFont);
      className.setHorizontalAlignment(SwingConstants.CENTER);

      baseHealth = new JLabel();
      baseHealth.setFont(headerFont);
      baseHealth.setHorizontalAlignment(SwingConstants.CENTER);

      //holds the class name, base health, and a divider
      JPanel classNamePanel = new JPanel(new GridLayout(0,1,TABLE_PADDING, TABLE_PADDING));
         classNamePanel.add(className);
         classNamePanel.add(baseHealth);
         classNamePanel.add(new JSeparator(SwingConstants.HORIZONTAL));

      //holds the ability descriptions
      JPanel abilityPanel = new JPanel(new GridLayout(ABILITIES_ROWS, ABILITES_COLUMNS));

      for(int i=0; i<ABILITIES_ROWS; i++)
      {
         for(int j=0; j<ABILITES_COLUMNS; j++)
         {
            JLabel label = new JLabel();
            label.setFont(statsFont);
            label.setHorizontalAlignment(SwingConstants.CENTER);

            jlAbilities[i][j] = label;
            abilityPanel.add(label);
         }
      }

      jlAbilities[0][0].setText("Ability Name");
      jlAbilities[0][0].setFont(headerFont);

      jlAbilities[0][1].setText("Description");
      jlAbilities[0][1].setFont(headerFont);

      //will hold the selection panel and the name panel
      JPanel bottomPanel = new JPanel(new GridLayout(2,0));

         JPanel namePanel = new JPanel(new FlowLayout());
            jtfName = new JTextField("Fighter Name",15);

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
      clsFrame.add(abilityPanel, BorderLayout.CENTER);
      clsFrame.add(bottomPanel, BorderLayout.SOUTH);

      updateText();

      clsFrame.setResizable(false);
      clsFrame.pack();
      clsFrame.setLocationRelativeTo(null);
      clsFrame.setVisible(true);
   }

   //updates the text to reflect the proper class stats/abilities
   private void updateText()
   {
      className.setText(classList.get(listPosition).getName());
      baseHealth.setText("Health: " + classList.get(listPosition).getBaseHealth());

      jlAbilities[1][0].setText(getCurrentFighter().getAbilityName(1));
      jlAbilities[1][1].setText(getCurrentFighter().getAbilityDescription(1));

      jlAbilities[2][0].setText(getCurrentFighter().getAbilityName(2));
      jlAbilities[2][1].setText(getCurrentFighter().getAbilityDescription(2));

      jlAbilities[3][0].setText(getCurrentFighter().getAbilityName(3));
      jlAbilities[3][1].setText(getCurrentFighter().getAbilityDescription(3));

      jlAbilities[4][0].setText(getCurrentFighter().getAbilityName(4));
      jlAbilities[4][1].setText(getCurrentFighter().getAbilityDescription(4));
   }

   private Fighter getCurrentFighter()
   {
      return classList.get(listPosition);
   }

   public void actionPerformed(ActionEvent ae)
   {
      String choice = ae.getActionCommand();

      //The choices endlessly cycle rather than stopping at either end
      if(choice == "<=")
      {
         if(listPosition == 0)
         {
            listPosition = classList.size() - 1; //endless cycle
         }
         else
         {
            listPosition--;
         }
         updateText();
      }

      else if(choice == "=>")
      {
         if(listPosition == classList.size() - 1) //endless cycle
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
         getCurrentFighter().setName(jtfName.getText());

         new MainMenu(getCurrentFighter());
         clsFrame.dispose();
         // TODO: 11/21/2015 set the class name to whatever the user entered, and write out(object output stream) the appropriate player
      }
   }
}