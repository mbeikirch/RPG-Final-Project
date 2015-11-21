public class Warrior extends Player
{
   protected String name;
   protected int level;
   protected int experience;

   //making a new Warrior, only parameter that's needed is name
   protected Warrior(String _name)
   {
      name = _name;
      level = 1;

      //setting level 1 health/power to 10/7 respectively
      changeBaseHealth(10);
      changeCurrentHealth(10);
      changeBasePower(7);
      changeCurrentPower(7);

      System.out.println("Warrior named " + name + " created!");
   }

   protected String getName()
   {
      return name;
   }

   protected String getDescription()
   {
      //Class name, base power, power on level, base health, health on level
      return  ("Warrior,7,3,10,5");
   }

   protected void setName(String name)
   {
      this.name = name;
   }

   protected void levelUp()
   {
      changeBaseHealth(5);
      changeBasePower(3);
      level++;
      experience = 0;
   }

   //returns player name, class, and level (probably only needed for the client list)
   protected String getInfo()
   {
      return (name + "Level: " + level + " Warrior");
   }

   //place holder abilities for now, 1 attack/1 heal
   protected int ability1()
   {
      return (currentPower * 2);
   }
   protected int ability2()
   {
      //messing around with heals, this heals based on current power as well as missing health, i.e. heals more the lower health you have
      changeCurrentHealth((currentPower + (baseHealth - currentHealth) / 3));
      return (currentHealth);
   }
   protected int ability3(){return -1;}
   protected int ability4(){return -1;}
}