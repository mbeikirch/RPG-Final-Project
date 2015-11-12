public class Wizard extends Player
{
   protected String name;
   protected int level;
   protected int experience;

   protected String description = "Class: Wizard\n" +
           "Starting Stats: Health: 7 , Power: 10\n" +
           "On Level Up: +3 Health, +5 Power";

   //making a new Wizard, only parameter that's needed is name
   protected Wizard(String _name)
   {
      name = _name;
      level = 1;

      //setting level 1 health/power to 7/10 respectively
      changeBaseHealth(7);
      changeBasePower(10);
      changeCurrentHealth(7);
      changeCurrentPower(10);

      System.out.println("Wizard named " + name + " created!");
   }

   protected String getName()
   {
      return name;
   }

   protected void setName(String name)
   {
      this.name = name;
   }

   protected void levelUp()
   {
      changeBaseHealth(3);
      changeCurrentHealth(baseHealth);
      changeBasePower(5);
      changeCurrentPower(basePower);
      level++;
      experience = 0;
   }

   //returns player name, class, and level (probably only needed for the client list)
   protected String getInfo()
   {
      return (name + "Level: " + level + " Wizard");
   }

   //place holder abilities for now, 1 attack/1 heal
   protected int ability1()
   {
      return (currentPower * 3);
   }
   protected int ability2()
   {
      //messing around with heals, this heals based on current power as well as missing health, i.e. heals more the lower health you have
      changeCurrentHealth((currentPower + (baseHealth - currentHealth) / 4));
      return (currentHealth);
   }
}