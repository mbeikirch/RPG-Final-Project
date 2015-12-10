import javax.swing.*;
import java.io.Serializable;

public class Warrior extends Fighter implements Serializable {
   protected String name;
   private final String CLASS_PATH = "/gamedata/players/player[@class='Warrior']";
   private GameDataParser myParser;
   private ImageIcon myIcon;
   private int ability1, ability2, ability3, ability4;

   //making a new Warrior, only parameter that's needed is name
   protected Warrior(String _name)
   {
      myParser = new GameDataParser(CLASS_PATH);

      name = _name;
      setBaseHealth(myParser.getBaseHealth());

      myIcon = myParser.getIcon();

      ability1 = myParser.getAbilityDamage(1);
      ability2 = myParser.getAbilityDamage(2);
      ability3 = myParser.getAbilityDamage(3);
      ability4 = myParser.getAbilityDamage(4);
   }

   protected String getClassName() { return "Warrior"; }

   protected String getName() {
      return name;
   }
   protected void setName(String _name) {
      name = _name;
   }

   protected int ability1() { return ability1; }

   protected int ability2() {
      return ability2;
   }

   protected int ability3() {
      return ability3;
   }

   protected int ability4() {
      return ability4;
   }

   protected ImageIcon getIcon() { return myIcon; }

   protected String getAbilityDescription(int num) { return (myParser.getAbilityDescription(num)); }

   protected String getAbilityName(int num) {
      return (myParser.getAbilityName(num));
   }
}
