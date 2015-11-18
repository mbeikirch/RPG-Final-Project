/**
 * Created by Josh
 */
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Hashtable;
import java.util.Map;

public class SelectionScreen
{

    //Attributes

    public SelectionScreen()
    {

        JFrame clsFrame = new JFrame("Class Selection Screen");
        clsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clsFrame.setSize(500,300);

        JPanel basePanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        JTextField jtfName = new JTextField("Name Your Class");

        Font titleFont = new Font("Book Antiqua", Font.BOLD, 24);
        Font statsFont = new Font("Apple Casual", Font.PLAIN, 14);
        Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
        map.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);

        Icon clsDefault = new ImageIcon("Icons/Default.jpg");
        JLabel jlCLSName = new JLabel(clsDefault);
        jlCLSName.setText("Select Your Class");
        jlCLSName.setHorizontalTextPosition(JLabel.CENTER);
        jlCLSName.setVerticalTextPosition(JLabel.TOP);

        JLabel jlCLSStats = new JLabel("Base Stats");
        jlCLSStats.setFont(titleFont.deriveFont(map));

        Icon strIcon = new ImageIcon("Icons/Strength.png");
        JLabel jlStr = new JLabel(strIcon); jlStr.setText("Strength: "); jlStr.setFont(statsFont);
        JLabel jlInt = new JLabel("Intelligence: "); jlInt.setFont(statsFont);
        JLabel jlDex = new JLabel("Dexterity: "); jlDex.setFont(statsFont);
        JLabel jlAP = new JLabel("Attack Power: "); jlAP.setFont(statsFont);
        JLabel jlSP = new JLabel("Spell Power: "); jlSP.setFont(statsFont);
        JLabel jlDP = new JLabel("Defense Power: "); jlDP.setFont(statsFont);

        //JTextArea jtaCLSImage = new JTextArea(5,20);

        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.PAGE_AXIS));
        leftPanel.add(jlCLSName);
        //leftPanel.add(jtaCLSImage);


        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.add(jlCLSStats); rightPanel.add(jlStr); rightPanel.add(jlInt); rightPanel.add(jlDex); rightPanel.add(jlAP);
        rightPanel.add(jlSP); rightPanel.add(jlDP);

        bottomPanel.add(jtfName);

        basePanel.setLayout(new GridLayout(1,1,4,4));

        basePanel.add(leftPanel);
        basePanel.add(rightPanel);


        //JButton jbLoad = new JButton("Load");
        //jpNewButtons.add(jbLoad);

        JButton jbPrev = new JButton("<=");
        bottomPanel.add(jbPrev);

        JButton jbNext = new JButton("=>");
        bottomPanel.add(jbNext);

        //Adding "new" buttons to Panel 2
        //bottomPanel.add(jpNewButtons,BorderLayout.SOUTH);

        //Adding Panel 2
        //add(jpButton,BorderLayout.SOUTH);

        basePanel.add(bottomPanel, BorderLayout.SOUTH);

        clsFrame.add(basePanel);
        clsFrame.setLocationRelativeTo(null);
        clsFrame.setVisible(true);

    }//end constructor

    public static void main (String[]args)
    {
        new SelectionScreen();

    }
}