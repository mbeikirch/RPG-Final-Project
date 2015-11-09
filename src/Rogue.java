public class Rogue extends Player
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
      System.out.println("Rogue named " + name + " created!");
   }

   protected void levelUp()
   {
      changeBaseHealth(2);
      changeCurrentHealth(baseHealth);
      changeBasePower(7);
      changeCurrentPower(basePower);
      level++;
      experience = 0;
   }

   //returns player name, class, and level (probably only needed for the client list)
   protected String getInfo()
   {
      return (name + ":" + "Level " + level + " Rogue" + ":");
   }

   //place holder abilities for now, 1 attack/1 heal
   protected int ability1()
   {
      return (currentPower * 5);
   }
   protected void ability2()
   {
      //messing around with heals
      changeCurrentHealth((currentPower / 4));
   }
}