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
      changeBasePower(7);

      System.out.println("Warrior named " + name + " created!");
   }

   protected void levelUp()
   {
      changeBaseHealth(5);
      changeCurrentHealth(baseHealth);
      changeBasePower(3);
      changeCurrentPower(basePower);
      level++;
      experience = 0;
   }

   //place holder abilities for now, 1 attack/1 heal
   protected int ability1()
   {
      return (currentPower * 2);
   }
   protected void ability2()
   {
      //messing around with heals, this heals based on current power as well as missing health, i.e. heals more the lower health you have
      changeCurrentHealth((currentPower + (baseHealth - currentHealth) / 3));
   }
}