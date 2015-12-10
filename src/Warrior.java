import javax.swing.*;
import java.io.Serializable;

public class Warrior extends Fighter implements Serializable {
   protected String name;
   private final String CLASS_PATH = "/gamedata/players/player[@class='Warrior']";
   private GameDataParser myParser;

   //making a new Warrior, only parameter that's needed is name
   protected Warrior(String _name) {
      myParser = new GameDataParser(CLASS_PATH);

      name = _name;

      setBaseHealth(myParser.getBaseHealth());

      System.out.println("Warrior named " + getName() + " created!");
   }

   protected String getClassName() { return "Warrior"; }

   protected String getName() {
      return name;
   }

   protected void setName(String _name) {
      name = _name;
   }

   protected int ability1() { return (myParser.getAbilityDamage(1)); }

   protected int ability2() {
      return (myParser.getAbilityDamage(2));
   }

   protected int ability3() {
      return (myParser.getAbilityDamage(3));
   }

   protected int ability4() {
      return (myParser.getAbilityDamage(4));
   }

   protected String getAbilityDescription(int num) {
      return (myParser.getAbilityDescription(num));
   }

   protected String getAbilityName(int num) {
      return (myParser.getAbilityName(num));
   }

   protected ImageIcon getIcon() { return (myParser.getIcon()); }
}
