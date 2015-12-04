import java.io.Serializable;

public class Rogue extends Player implements Serializable
{
   protected String name;
   protected int level;
   protected int experience;

   //making a new Rogue, only parameter that's needed is name
   protected Rogue(String _name)
   {
      name = _name;
      level = 1;

      //setting level 1 health/power to 5/12 respectively
      changeBaseHealth(5);
      changeBasePower(12);
      changeCurrentHealth(5);
      changeCurrentPower(12);

      System.out.println("Rogue named " + name + " created!");
   }

   protected String getName()
   {
      return name;
   }

   protected String getDescription()
   {
      //Class name, base power, power on level, base health, health on level
      return  ("Rogue,12,7,5,2");
   }

   protected void setName(String name)
   {
      this.name = name;
   }

   protected void levelUp()
   {
      changeBaseHealth(2);
      changeBasePower(7);
      level++;
      experience = 0;
   }

   //returns player name, class, and level (probably only needed for the client list)
   protected String getInfo()
   {
      return (name + "Level: " + level + " Rogue");
   }

   //place holder abilities for now, 1 attack/1 heal
   protected int ability1()
   {
      return (currentPower * 5);
   }
   protected int ability2()
   {
      //messing around with heals
      changeCurrentHealth((currentPower / 4));
      return (currentHealth);
   }
   protected int ability3(){return -1;}
   protected int ability4(){return -1;}
}