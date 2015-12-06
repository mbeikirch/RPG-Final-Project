import java.io.Serializable;

public class Wizard extends Fighter implements Serializable
{
   protected String name;
   private final String CLASS_PATH = "/gamedata/players/player[@class='Wizard']";
   private GameDataParser myParser;

   //making a new Wizard, only parameter that's needed is name
   protected Wizard(String _name)
   {
      myParser = new GameDataParser(CLASS_PATH);

      name = _name;

      setBaseHealth(myParser.getBaseHealth());

      System.out.println("Wizard named " + getName() + " created!");
   }

   protected String getName()
   {
      return name;
   }
   protected void setName(String _name) { name = _name; }

   protected int ability1()
   {
      return (myParser.getAbilityDamage(1));
   }
   protected int ability2() { return (myParser.getAbilityDamage(2)); }
   protected int ability3()
   {
      return (myParser.getAbilityDamage(3));
   }
   protected int ability4()
   {
      return (myParser.getAbilityDamage(4));
   }

   protected String getAbilityDescription(int num)
   {
      return (myParser.getAbilityDescription(num));
   }
   protected String getAbilityName(int num)
   {
      return (myParser.getAbilityName(num));
   }
}